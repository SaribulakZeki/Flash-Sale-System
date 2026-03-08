import { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const [products, setProducts] = useState([]);
  const [message, setMessage] = useState({ text: "", type: "" }); 

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await axios.get('http://localhost:8081/api/products');
      setProducts(response.data);
      console.log("Ürünler geldi:", response.data);
    } catch (error) {
      console.error("Hata oluştu:", error);
      setMessage({ 
        text: "Backend'e ulaşılamadı! Product Service çalışıyor mu?", 
        type: "danger" 
      });
    }
  };

  const handleBuy = async (productId, productName) => {
    try {
      setMessage({ 
        text: `${productName} için işlem yapılıyor...`, 
        type: "info" 
      });
 
      await axios.post(`http://localhost:8081/api/products/${productId}/buy`);
     
      setMessage({ 
        text: `Başarılı! "${productName}" siparişiniz alındı.`, 
        type: "success" 
      });

      setTimeout(() => setMessage({ text: "", type: "" }), 3000);

    } catch (error) {
      console.error("Satın alma hatası:", error);
      setMessage({ 
        text: "Satın alma başarısız oldu.", 
        type: "danger" 
      });
    }
  };

  return (
    <div className="container mt-5">
      <h1 className="text-center text-primary mb-4">Flash Sale Sistemi</h1>
      
      {}
      {message.text && (
        <div className={`alert text-center alert-${message.type}`}>
          {message.text}
        </div>
      )}

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
                  Hemen Satın Al
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