 // İlaç listesini LocalStorage'dan çek
let drugs = JSON.parse(localStorage.getItem("drugs") || "[]");

// DOM Elementleri
const dosesDiv = document.getElementById("doses");
const addDoseBtn = document.getElementById("addDoseBtn");
const saveBtn = document.getElementById("saveBtn");
const drugList = document.getElementById("drugList");

// --------------------------
// 1. Doz satırı oluşturma fonksiyonu
// --------------------------
function addDoseRow() {
    const row = document.createElement("div");
    row.className = "dose-row";

    row.innerHTML = `
        <input type="time" class="doseTime" required>
        <select class="doseFood">
            <option value="Aç">Aç Karnına</option>
            <option value="Tok">Tok Karnına</option>
        </select>
    `;

    dosesDiv.appendChild(row);
}

// Sayfa açılışında otomatik 1 tane ekle
addDoseRow();

// Butona tıklayınca yeni satır ekle
addDoseBtn.addEventListener("click", addDoseRow);

// --------------------------
// 2. İlaç Kaydetme Fonksiyonu
// --------------------------
saveBtn.addEventListener("click", () => {
    
    // Girdileri al
    const name = document.getElementById("drugName").value.trim();
    const start = document.getElementById("drugStart").value;
    const stock = Number(document.getElementById("drugStock").value);

    // Basit doğrulama
    if (!name || !start || stock <= 0) {
        alert("Lütfen İlaç Adı, Tarih ve Stok bilgilerini eksiksiz girin.");
        return;
    }

    // Dozları (saatleri) topla
    const timeInputs = document.querySelectorAll(".doseTime");
    const foodInputs = document.querySelectorAll(".doseFood");
    let doses = [];

    for (let i = 0; i < timeInputs.length; i++) {
        const tVal = timeInputs[i].value;
        const fVal = foodInputs[i].value;

        // Sadece saati seçilmiş olanları listeye ekle
        if (tVal) {
            doses.push({
                time: tVal,
                food: fVal
            });
        }
    }

    if (doses.length === 0) {
        alert("Lütfen en az bir doz saati seçin!");
        return;
    }

    // İlaç objesini oluştur
    const drug = {
        id: Date.now(),
        name: name,
        start: start,
        stock: stock,
        doses: doses, // Doz listesi
        dailyCount: doses.length // Günde kaç kez alınacağı otomatik hesaplandı
    };

    // Listeye ekle ve kaydet
    drugs.push(drug);
    localStorage.setItem("drugs", JSON.stringify(drugs));

    // Arayüzü güncelle
    renderDrugs();
    
    // Formu temizle ve kullanıcıya bilgi ver
    alert("İlaç başarıyla kaydedildi!");
    
    // Inputları sıfırla
    document.getElementById("drugName").value = "";
    document.getElementById("drugStock").value = "10";
    dosesDiv.innerHTML = "";
    addDoseRow(); // Temizlendikten sonra 1 boş satır ekle
});

// --------------------------
// 3. Listeyi Ekrana Basma
// --------------------------
function renderDrugs() {
    drugList.innerHTML = "";

    // Listeyi ters çevirip göster (en son eklenen en üstte)
    drugs.slice().reverse().forEach(drug => {
        const card = document.createElement("div");
        card.className = "drug-card";

        // Stok bitiş tahmini (Opsiyonel Bilgi)
        const daysLasting = Math.floor(drug.stock / drug.dailyCount);

        card.innerHTML = `
            <div style="display:flex; justify-content:space-between; align-items:center;">
                <h3 style="margin:0;">${drug.name}</h3>
                <small style="color: #666;">${drug.start}</small>
            </div>
            <hr style="border: 0; border-top: 1px solid #eee; margin: 10px 0;">
            
            <strong>Stok:</strong> ${drug.stock} adet<br>
            <strong>Günlük Doz:</strong> ${drug.dailyCount} kez alınacak<br>
            <small style="color:red;">(Stok yaklaşık ${daysLasting} gün yetecek)</small>

            <div style="margin-top:10px; background:#f1f1f1; padding:10px; border-radius:5px;">
                <strong>Saatler:</strong>
                <ul>
                    ${drug.doses.map(d => `<li>Saat <b>${d.time}</b> - ${d.food}</li>`).join("")}
                </ul>
            </div>
            
            <button onclick="deleteDrug(${drug.id})" style="background:#dc3545; margin-top:10px; padding:8px;">Sil</button>
        `;

        drugList.appendChild(card);
    });
}

// --------------------------
// 4. Silme Fonksiyonu
// --------------------------
function deleteDrug(id) {
    if(confirm("Bu ilacı silmek istediğine emin misin?")) {
        drugs = drugs.filter(d => d.id !== id);
        localStorage.setItem("drugs", JSON.stringify(drugs));
        renderDrugs();
    }
}

// Sayfa ilk açıldığında listeyi yükle
renderDrugs();