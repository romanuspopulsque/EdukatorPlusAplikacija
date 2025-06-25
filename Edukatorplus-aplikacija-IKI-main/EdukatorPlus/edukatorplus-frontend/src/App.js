import React, { useEffect, useState } from "react";

const API_URL = "https://edukatorplusaplikacija-2.onrender.com";

function App() {
  const [polaznici, setPolaznici] = useState([]);
  const [radionice, setRadionice] = useState([]);
  const [prisustva, setPrisustva] = useState([]);
  const [status, setStatus] = useState("PRISUTAN");
  const [polaznikId, setPolaznikId] = useState("");
  const [radionicaId, setRadionicaId] = useState("");

  useEffect(() => {
    fetch(`${API_URL}/api/polaznici`)
      .then((res) => {
        if (!res.ok) throw new Error("Greška kod dohvaćanja polaznika");
        return res.json();
      })
      .then(setPolaznici)
      .catch((err) => console.error(err));

    fetch(`${API_URL}/api/radionice`)
      .then((res) => {
        if (!res.ok) throw new Error("Greška kod dohvaćanja radionica");
        return res.json();
      })
      .then(setRadionice)
      .catch((err) => console.error(err));

    fetch(`${API_URL}/api/prisustva`)
      .then((res) => {
        if (!res.ok) throw new Error("Greška kod dohvaćanja prisustava");
        return res.json();
      })
      .then(setPrisustva)
      .catch((err) => console.error(err));
  }, []);

  const handleEvidentiraj = () => {
    const params = new URLSearchParams({
      polaznikId,
      radionicaId,
      status,
    });
    fetch(`${API_URL}/api/prisustva/evidentiraj?${params.toString()}`, {
      method: "POST",
    })
      .then((res) => {
        if (res.ok) {
          alert("Prisustvo evidentirano!");
          // Osvježi listu prisustava odmah
          return fetch(`${API_URL}/api/prisustva`)
            .then((res) => res.json())
            .then(setPrisustva);
        } else {
          alert("Greška pri evidenciji.");
        }
      })
      .catch((err) => {
        console.error("Greška kod slanja prisustva:", err);
        alert("Greška pri evidenciji.");
      });
  };

  return (
    <div className="p-6 max-w-3xl mx-auto">
      <h1 className="text-3xl font-bold mb-4">Evidencija prisustva</h1>

      <select
        className="border p-2 w-full mb-2"
        value={polaznikId}
        onChange={(e) => setPolaznikId(e.target.value)}
      >
        <option value="">Odaberi polaznika</option>
        {polaznici.map((p) => (
          <option key={p.id} value={p.id}>
            {p.ime} {p.prezime}
          </option>
        ))}
      </select>

      <select
        className="border p-2 w-full mb-2"
        value={radionicaId}
        onChange={(e) => setRadionicaId(e.target.value)}
      >
        <option value="">Odaberi radionicu</option>
        {radionice.map((r) => (
          <option key={r.id} value={r.id}>
            {r.naziv} ({r.datum})
          </option>
        ))}
      </select>

      <select
        className="border p-2 w-full mb-4"
        value={status}
        onChange={(e) => setStatus(e.target.value)}
      >
        <option value="PRISUTAN">PRISUTAN</option>
        <option value="ODSUTAN">ODSUTAN</option>
        <option value="OPRAVDANO">OPRAVDANO</option>
        <option value="NEOPRAVDANO">NEOPRAVDANO</option>
      </select>

      <button
        className="bg-blue-600 text-white px-4 py-2 rounded"
        onClick={handleEvidentiraj}
      >
        Evidentiraj prisustvo
      </button>

      {/* Prikaz prisustava */}
      <div className="mt-8">
        <h2 className="text-xl font-semibold mb-2">Evidentirana prisustva</h2>
        <ul className="list-disc pl-5">
          {prisustva.map((p) => (
            <li key={p.id}>
              Polaznik ID: {p.polaznik?.id}, Radionica ID: {p.radionica?.id}, Status: {p.status}
            </li>
          ))}
        </ul>
      </div>

      {/* Prikaz svih polaznika */}
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

      {/* Prikaz svih radionica */}
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
