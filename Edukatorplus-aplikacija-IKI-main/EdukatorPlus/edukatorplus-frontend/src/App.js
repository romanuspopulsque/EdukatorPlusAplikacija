import React, { useEffect, useState } from "react";

const API_URL = "https://edukatorplusaplikacija-2.onrender.com";

function App() {
  const [polaznici, setPolaznici] = useState([]);
  const [radionice, setRadionice] = useState([]);
  const [prisustva, setPrisustva] = useState([]);

  const [polaznikId, setPolaznikId] = useState("");
  const [radionicaId, setRadionicaId] = useState("");
  const [status, setStatus] = useState("PRISUTAN");

  const [noviPolaznik, setNoviPolaznik] = useState({
    ime: "", prezime: "", email: "", telefon: "", godina_rodenja: ""
  });
  const [novaRadionica, setNovaRadionica] = useState({
    naziv: "", opis: "", datum: "", trajanje: ""
  });

  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const fetchData = () => {
    setLoading(true);
    setError(null);
    console.log("📡 Pokrećem fetchData…");

    Promise.all([
      fetch(`${API_URL}/api/polaznici`, { credentials: 'include' }),
      fetch(`${API_URL}/api/radionice`, { credentials: 'include' }),
      fetch(`${API_URL}/api/prisustva`, { credentials: 'include' })
    ])
      .then(async ([r1, r2, r3]) => {
        if (!r1.ok) throw new Error(`Polaznici fetch error: ${r1.status}`);
        if (!r2.ok) throw new Error(`Radionice fetch error: ${r2.status}`);
        if (!r3.ok) throw new Error(`Prisustva fetch error: ${r3.status}`);
        return Promise.all([r1.json(), r2.json(), r3.json()]);
      })
      .then(([p, r, pr]) => {
        setPolaznici(p);
        setRadionice(r);
        setPrisustva(pr);
        console.log("✅ fetchData uspješan");
      })
      .catch(err => {
        console.error("❌ Greška pri fetchData:", err);
        setError(err.message);
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => { fetchData(); }, []);

  const dodajPolaznika = () => {
    if (!noviPolaznik.ime || !noviPolaznik.prezime) {
      alert("Ime i prezime su obavezni!");
      return;
    }
    fetch(`${API_URL}/api/polaznici`, {
      method: "POST",
      credentials: 'include',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        ime: noviPolaznik.ime,
        prezime: noviPolaznik.prezime,
        email: noviPolaznik.email,
        telefon: noviPolaznik.telefon,
        godina_rodenja: Number(noviPolaznik.godina_rodenja),
      })
    })
      .then(res => {
        if (!res.ok) throw new Error(`Greška (${res.status}) pri dodavanju polaznika`);
        return res.json();
      })
      .then(() => {
        alert("Polaznik uspješno dodan!");
        setNoviPolaznik({ ime: "", prezime: "", email: "", telefon: "", godina_rodenja: "" });
        fetchData();
      })
      .catch(e => alert(e.message));
  };

  const dodajRadionicu = () => {
    if (!novaRadionica.naziv || !novaRadionica.datum) {
      alert("Naziv i datum su obavezni!");
      return;
    }
    fetch(`${API_URL}/api/radionice`, {
      method: "POST",
      credentials: 'include',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        naziv: novaRadionica.naziv,
        opis: novaRadionica.opis,
        datum: novaRadionica.datum,
        trajanje: Number(novaRadionica.trajanje)
      })
    })
      .then(res => {
        if (!res.ok) throw new Error(`Greška (${res.status}) pri dodavanju radionice`);
        return res.json();
      })
      .then(() => {
        alert("Radionica uspješno dodana!");
        setNovaRadionica({ naziv: "", opis: "", datum: "", trajanje: "" });
        fetchData();
      })
      .catch(e => alert(e.message));
  };

  const evidentirajPrisustvo = () => {
    if (!polaznikId || !radionicaId) {
      alert("Molim te odaberi polaznika i radionicu!");
      return;
    }
    fetch(`${API_URL}/api/prisustva/evidentiraj?polaznikId=${polaznikId}&radionicaId=${radionicaId}&status=${status}`, {
      method: "POST",
      credentials: 'include',
    })
      .then(res => {
        if (!res.ok) throw new Error(`Greška (${res.status}) pri evidentiranju prisustva`);
        return res.json();
      })
      .then(() => {
        alert("Prisustvo evidentirano!");
        fetchData();
      })
      .catch(e => alert(e.message));
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p style={{ color: "red" }}>Error: {error}</p>;

  return (
    <div className="p-6 max-w-3xl mx-auto">
      <h1 className="text-3xl font-bold mb-4">Evidencija prisustva</h1>

      <div className="mt-4">
        <h2 className="text-xl font-semibold mb-2">Polaznici</h2>
        <ul className="list-disc pl-5">
          {polaznici.map(p => (
            <li key={p.id}>{p.ime} {p.prezime}</li>
          ))}
        </ul>

        <h2 className="text-xl font-semibold mt-4 mb-2">Radionice</h2>
        <ul className="list-disc pl-5">
          {radionice.map(r => (
            <li key={r.id}>{r.naziv} ({r.datum})</li>
          ))}
        </ul>

        <h2 className="text-xl font-semibold mt-4 mb-2">Prisustva</h2>
        <ul className="list-disc pl-5">
          {prisustva.map(pr => (
            <li key={pr.id}>Polaznik #{pr.polaznikId} - Radionica #{pr.radionicaId}: {pr.status}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default App;
