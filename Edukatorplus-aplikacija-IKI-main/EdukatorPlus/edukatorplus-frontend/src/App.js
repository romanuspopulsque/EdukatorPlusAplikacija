import React, { useEffect, useState } from "react";

function App() {
  const [polaznici, setPolaznici] = useState([]);
  const [radionice, setRadionice] = useState([]);
  const [status, setStatus] = useState("PRISUTAN");
  const [polaznikId, setPolaznikId] = useState("");
  const [radionicaId, setRadionicaId] = useState("");

  useEffect(() => {
    fetch("/api/polaznici")
      .then((res) => res.json())
      .then(setPolaznici);

    fetch("/api/radionice")
      .then((res) => res.json())
      .then(setRadionice);
  }, []);

  const handleEvidentiraj = () => {
    const params = new URLSearchParams({
      polaznikId,
      radionicaId,
      status,
    });
    fetch(`/api/prisustva/evidentiraj?${params.toString()}`, {
      method: "POST",
    })
      .then((res) => {
        if (res.ok) alert("Prisustvo evidentirano!");
        else alert("Greška pri evidenciji.");
      });
  };

  return (
    <div className="p-6 max-w-2xl mx-auto">
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
      </select>

      <button
        className="bg-blue-600 text-white px-4 py-2 rounded"
        onClick={handleEvidentiraj}
      >
        Evidentiraj prisustvo
      </button>
    </div>
  );
}

export default App;
