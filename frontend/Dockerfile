# ---------- Etapa de build ----------
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build -- --configuration production

# ---------- Etapa de runtime ----------
FROM node:20-alpine AS runtime
WORKDIR /app

# Solo dependencias de producción (incluye express, que usa el server SSR)
COPY package*.json ./
RUN npm ci --omit=dev

# Copia el build (browser + server)
COPY --from=build /app/dist ./dist

EXPOSE 4000
CMD ["node", "dist/frontend/server/server.mjs"]
