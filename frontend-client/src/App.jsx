import { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  // 1. Durum (State) Tanımları
  const [products, setProducts] = useState([]); // Ürünleri tutacak liste
  const [message, setMessage] = useState("");   // Ekrana bilgi mesajı basmak için

  // 2. Sayfa Yüklendiğinde Çalışacak Kod (useEffect)
  useEffect(() => {
    fetchProducts();
  }, []);

  // Backend'den ürünleri çeken fonksiyon
  const fetchProducts = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/products');
      setProducts(response.data);
      console.log("Ürünler geldi:", response.data);
    } catch (error) {
      console.error("Hata oluştu:", error);
      setMessage("Backend'e ulaşılamadı! Product Service çalışıyor mu?");
    }
  };

  // 3. Satın Alma Fonksiyonu
  const handleBuy = async (productId, productName) => {
    try {
      setMessage(`⏳ ${productName} için işlem yapılıyor...`);
      
      // POST isteği atıyoruz (Backend'deki satın alma endpoint'i)
      await axios.post(`http://localhost:8081/api/products/${productId}/buy`);
      
      setMessage(`✅ Başarılı! "${productName}" siparişiniz alındı.`);
      
      // 3 saniye sonra mesajı temizle
      setTimeout(() => setMessage(""), 3000);

    } catch (error) {
      console.error("Satın alma hatası:", error);
      setMessage("❌ Satın alma başarısız oldu.");
    }
  };

  // 4. Ekrana Çizilen Kısım (HTML/JSX)
  return (
    <div className="container mt-5">
      <h1 className="text-center text-primary mb-4">🚀 Flash Sale Sistemi</h1>
      
      {/* Bilgi Mesajı Kutusu */}
      {message && (
        <div className={`alert text-center ${message.includes('❌') ? 'alert-danger' : 'alert-success'}`}>
          {message}
        </div>
      )}

      {/* Ürün Listesi */}
      <div className="row">
        {products.map((product) => (
          <div key={product.id} className="col-md-6 mb-4">
            <div className="card shadow-sm">
              <div className="card-body text-center">
                <h3 className="card-title">{product.name}</h3>
                <p className="card-text text-muted">{product.description}</p>
                <h4 className="text-success">{product.price} TL</h4>
                <p>Stok: <strong>{product.quantity}</strong></p>
                
                <button 
                  className="btn btn-primary btn-lg w-100"
                  onClick={() => handleBuy(product.id, product.name)}
                >
                  ⚡ Hemen Satın Al
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;