package ffos.rsimunovic.edukatorplus.controller;

import ffos.rsimunovic.edukatorplus.DTO.PrisustvoDTO;
import ffos.rsimunovic.edukatorplus.service.PrisustvoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://edukatorplusaplikacija-3.onrender.com")
@RestController
@RequestMapping("/api/prisustva")
public class PrisustvoController {


    @Autowired
    private PrisustvoService prisustvoService;

    @Operation(summary = "Dohvati sva prisustva")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Uspješan dohvat"),
        @ApiResponse(responseCode = "500", description = "Interna greška servera")
    })
    @GetMapping
    public ResponseEntity<List<PrisustvoDTO>> getAll() {
        List<PrisustvoDTO> lista = prisustvoService.getAll();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Evidentiraj prisustvo polaznika na radionici")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prisustvo evidentirano"),
        @ApiResponse(responseCode = "400", description = "Neispravan zahtjev"),
        @ApiResponse(responseCode = "404", description = "Polaznik ili radionica nisu pronađeni")
    })
    @PostMapping
    public ResponseEntity<PrisustvoDTO> create(@RequestBody PrisustvoDTO dto) {
        PrisustvoDTO rezultat = prisustvoService.create(dto);
        if (rezultat == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(rezultat);
    }

    @Operation(summary = "Evidentiraj prisustvo polaznika na radionici s parametrima")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Prisustvo uspješno evidentirano"),
        @ApiResponse(responseCode = "400", description = "Neispravan zahtjev – nedostaju parametri ili nisu valjani"),
        @ApiResponse(responseCode = "404", description = "Radionica ili polaznik nisu pronađeni")
    })
    @PostMapping("/evidentiraj")
    public ResponseEntity<PrisustvoDTO> evidentirajPrisustvo(@RequestParam Long radionicaId,
                                                             @RequestParam Long polaznikId,
                                                             @RequestParam String status) {
        if (radionicaId == null || polaznikId == null || status == null || status.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        PrisustvoDTO rezultat = prisustvoService.evidentirajPrisustvo(radionicaId, polaznikId, status);
        if (rezultat == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(rezultat);
    }

    @Operation(summary = "Dohvati prisustva za određenu radionicu")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Uspješan dohvat prisustava"),
        @ApiResponse(responseCode = "404", description = "Radionica nije pronađena ili nema prisustava")
    })
    @GetMapping("/radionica/{radionicaId}")
    public ResponseEntity<List<PrisustvoDTO>> getPrisustvaByRadionica(@PathVariable Long radionicaId) {
        List<PrisustvoDTO> prisustva = prisustvoService.getByRadionicaId(radionicaId);
        if (prisustva == null || prisustva.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(prisustva);
    }
    
    @Operation(summary = "Ažuriraj prisustvo po ID-u")
@PutMapping("/{id}")
public ResponseEntity<PrisustvoDTO> update(@PathVariable Long id, @RequestBody PrisustvoDTO dto) {
    PrisustvoDTO updated = prisustvoService.update(id, dto);
    if (updated == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(updated);
}

@Operation(summary = "Obriši prisustvo po ID-u")
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    boolean deleted = prisustvoService.delete(id);
    if (!deleted) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.noContent().build();
}

    @Operation(summary = "Dohvati prisustva po statusu")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prisustva uspješno dohvaćena"),
        @ApiResponse(responseCode = "400", description = "Neispravan status prisustva")
    })
    @GetMapping("/status")
    public ResponseEntity<List<PrisustvoDTO>> getByStatus(@RequestParam String status) {
        // Pokušavamo dohvatiti status u enumu
        try {
            List<PrisustvoDTO> lista = prisustvoService.getByStatus(status);
            if (lista.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 ako nema ništa
            }
            return ResponseEntity.ok(lista);
        } catch (IllegalArgumentException ex) {
            // Ako status nije validan enum, vrati 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

