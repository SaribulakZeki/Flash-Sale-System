# node:22-alpine yerine tam sürüm kullanalım, daha kararlıdır
FROM node:22-bullseye-slim

WORKDIR /app

# Sadece paketleri kopyala
COPY package.json package-lock.json ./

# Temiz kurulum yap
RUN npm install

# Projeyi kopyala
COPY . .

EXPOSE 5173

# Vite'ın dışarıdan erişilmesi için gerekli parametreleri buraya çiviliyoruz
CMD ["npx", "vite", "--host", "0.0.0.0", "--port", "5173"]