import React, { useEffect, useState } from "react";

const API_URL = "https://edukatorplusaplikacija-2.onrender.com";

function App() {
  // Statični podaci
  const [polaznici, setPolaznici] = useState([]);
  const [radionice, setRadionice] = useState([]);
  const [prisustva, setPrisustva] = useState([]);

  // Odabrani za prisustvo
  const [polaznikId, setPolaznikId] = useState("");
  const [radionicaId, setRadionicaId] = useState("");
  const [status, setStatus] = useState("PRISUTAN");

  // Novi polaznik
  const [noviPolaznik, setNoviPolaznik] = useState({
    ime: "",
    prezime: "",
    email: "",
    telefon: "",
    godina_rodenja: "",
  });

  // Nova radionica
  const [novaRadionica, setNovaRadionica] = useState({
    naziv: "",
    opis: "",
    datum: "",
    trajanje: "",
  });

  // Error i loading state
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  // Fetchaj sve podatke
  const fetchData = () => {
    setLoading(true);
    setError(null);

    Promise.all([
      fetch(`${API_URL}/api/polaznici`).then((res) =>
        res.ok ? res.json() : Promise.reject("Ne mogu dohvatiti polaznike")
      ),
      fetch(`${API_URL}/api/radionice`).then((res) =>
        res.ok ? res.json() : Promise.reject("Ne mogu dohvatiti radionice")
      ),
      fetch(`${API_URL}/api/prisustva`).then((res) =>
        res.ok ? res.json() : Promise.reject("Ne mogu dohvatiti prisustva")
      ),
    ])
      .then(([polazniciData, radioniceData, prisustvaData]) => {
        setPolaznici(polazniciData);
        setRadionice(radioniceData);
        setPrisustva(prisustvaData);
      })
      .catch((err) => setError(err.toString()))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchData();
  }, []);

  // Funkcija za dodavanje polaznika
  const dodajPolaznika = () => {
    if (!noviPolaznik.ime || !noviPolaznik.prezime) {
      alert("Ime i prezime su obavezni!");
      return;
    }
    fetch(`${API_URL}/api/polaznici`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        ime: noviPolaznik.ime,
        prezime: noviPolaznik.prezime,
        email: noviPolaznik.email,
        telefon: noviPolaznik.telefon,
        godina_rodenja: Number(noviPolaznik.godina_rodenja),
      }),
    })
      .then((res) => {
        if (!res.ok) throw new Error("Greška pri dodavanju polaznika");
        return res.json();
      })
      .then(() => {
        alert("Polaznik uspješno dodan!");
        setNoviPolaznik({ ime: "", prezime: "", email: "", telefon: "", godina_rodenja: "" });
        fetchData();
      })
      .catch((e) => alert(e.message));
  };

  // Funkcija za dodavanje radionice
  const dodajRadionicu = () => {
    if (!novaRadionica.naziv || !novaRadionica.datum) {
      alert("Naziv i datum su obavezni!");
      return;
    }
    fetch(`${API_URL}/api/radionice`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        naziv: novaRadionica.naziv,
        opis: novaRadionica.opis,
        datum: novaRadionica.datum,
        trajanje: Number(novaRadionica.trajanje),
      }),
    })
      .then((res) => {
        if (!res.ok) throw new Error("Greška pri dodavanju radionice");
        return res.json();
      })
      .then(() => {
        alert("Radionica uspješno dodana!");
        setNovaRadionica({ naziv: "", opis: "", datum: "", trajanje: "" });
        fetchData();
      })
      .catch((e) => alert(e.message));
  };

  // Evidentiraj prisustvo
  const evidentirajPrisustvo = () => {
    if (!polaznikId || !radionicaId) {
      alert("Molim te odaberi polaznika i radionicu!");
      return;
    }
    fetch(`${API_URL}/api/prisustva/evidentiraj?polaznikId=${polaznikId}&radionicaId=${radionicaId}&status=${status}`, {
      method: "POST",
    })
      .then((res) => {
        if (!res.ok) throw new Error("Greška pri evidentiranju prisustva");
        return res.json();
      })
      .then(() => {
        alert("Prisustvo evidentirano!");
        fetchData();
      })
      .catch((e) => alert(e.message));
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p style={{ color: "red" }}>Error: {error}</p>;

  return (
    <div className="p-6 max-w-3xl mx-auto">
      <h1 className="text-3xl font-bold mb-4">Evidencija prisustva</h1>

      {/* Odabir polaznika */}
      <select value={polaznikId} onChange={(e) => setPolaznikId(e.target.value)} className="border p-2 w-full mb-2">
        <option value="">Odaberi polaznika</option>
        {polaznici.map((p) => (
          <option key={p.id} value={p.id}>
            {p.ime} {p.prezime}
          </option>
        ))}
      </select>

      {/* Odabir radionice */}
      <select value={radionicaId} onChange={(e) => setRadionicaId(e.target.value)} className="border p-2 w-full mb-2">
        <option value="">Odaberi radionicu</option>
        {radionice.map((r) => (
          <option key={r.id} value={r.id}>
            {r.naziv} ({r.datum})
          </option>
        ))}
      </select>

      {/* Odabir statusa */}
      <select value={status} onChange={(e) => setStatus(e.target.value)} className="border p-2 w-full mb-4">
        <option value="PRISUTAN">PRISUTAN</option>
        <option value="ODSUTAN">ODSUTAN</option>
        <option value="OPRAVDANO">OPRAVDANO</option>
        <option value="NEOPRAVDANO">NEOPRAVDANO</option>
      </select>

      <button onClick={evidentirajPrisustvo} className="bg-blue-600 text-white px-4 py-2 rounded mb-6">
        Evidentiraj prisustvo
      </button>

      {/* Forma za dodavanje polaznika */}
      <div className="mb-8 p-4 border rounded shadow-sm">
        <h2 className="text-2xl font-semibold mb-4">Dodaj novog polaznika</h2>
        <input
          type="text"
          placeholder="Ime"
          value={noviPolaznik.ime}
          onChange={(e) => setNoviPolaznik({ ...noviPolaznik, ime: e.target.value })}
          className="border p-2 w-full mb-2"
        />
        <input
          type="text"
          placeholder="Prezime"
          value={noviPolaznik.prezime}
          onChange={(e) => setNoviPolaznik({ ...noviPolaznik, prezime: e.target.value })}
          className="border p-2 w-full mb-2"
        />
        <input
          type="email"
          placeholder="Email"
          value={noviPolaznik.email}
          onChange={(e) => setNoviPolaznik({ ...noviPolaznik, email: e.target.value })}
          className="border p-2 w-full mb-2"
        />
        <input
          type="tel"
          placeholder="Telefon"
          value={noviPolaznik.telefon}
          onChange={(e) => setNoviPolaznik({ ...noviPolaznik, telefon: e.target.value })}
          className="border p-2 w-full mb-2"
        />
        <input
          type="number"
          placeholder="Godina rođenja"
          value={noviPolaznik.godina_rodenja}
          onChange={(e) => setNoviPolaznik({ ...noviPolaznik, godina_rodenja: e.target.value })}
          className="border p-2 w-full mb-2"
        />
        <button onClick={dodajPolaznika} className="bg-green-600 text-white px-4 py-2 rounded">
          Dodaj polaznika
        </button>
      </div>

      {/* Forma za dodavanje radionice */}
      <div className="mb-8 p-4 border rounded shadow-sm">
        <h2 className="text-2xl font-semibold mb-4">Dodaj novu radionicu</h2>
        <input
          type="text"
          placeholder="Naziv"
          value={novaRadionica.naziv}
          onChange={(e) => setNovaRadionica({ ...novaRadionica, naziv: e.target.value })}
          className="border p-2 w-full mb-2"
        />
        <textarea
          placeholder="Opis"
          value={novaRadionica.opis}
          onChange={(e) => setNovaRadionica({ ...novaRadionica, opis: e.target.value })}
          className="border p-2 w-full mb-2"
          rows={3}
        />
        <input
          type="date"
          placeholder="Datum"
          value={novaRadionica.datum}
          onChange={(e) => setNovaRadionica({ ...novaRadionica, datum: e.target.value })}
          className="border p-2 w-full mb-2"
        />
        <input
          type="number"
          placeholder="Trajanje (sati)"
          value={novaRadionica.trajanje}
          onChange={(e) => setNovaRadionica({ ...novaRadionica, trajanje: e.target.value })}
          className="border p-2 w-full mb-2"
        />
        <button onClick={dodajRadionicu} className="bg-green-600 text-white px-4 py-2 rounded">
          Dodaj radionicu
        </button>
      </div>

      {/* Prikaz prisustva */}
      <div>
        <h2 className="text-xl font-semibold mb-2">Evidentirana prisustva</h2>
        <ul className="list-disc pl-5">
          {prisustva.map((p) => (
            <li key={p.id}>
              Polaznik: {p.polaznik?.ime} {p.polaznik?.prezime}, Radionica: {p.radionica?.naziv}, Status: {p.status}
            </li>
          ))}
        </ul>
      </div>

      {/* Prikaz polaznika */}
      <div className="mt-8">
        <h2 className="text-xl font-semibold mb-2">Svi polaznici</h2>
        <ul className="list-disc pl-5">
          {polaznici.map((p) => (
            <li key={p.id}>
              {p.ime} {p.prezime} – {p.email}, {p.telefon}
            </li>
          ))}
        </ul>
      </div>

      {/* Prikaz radionica */}
      <div className="mt-8">
        <h2 className="text-xl font-semibold mb-2">Sve radionice</h2>
        <ul className="list-disc pl-5">
          {radionice.map((r) => (
            <li key={r.id}>
              {r.naziv} – {r.opis} ({r.datum}, {r.trajanje}h)
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default App;
