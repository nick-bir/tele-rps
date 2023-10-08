FROM node:20-buster as build

WORKDIR /var/www/tele-rps-gui

COPY package*.json ./
RUN npm i

COPY src/ src/
COPY tsconfig.json ./
COPY tsconfig.node.json ./
COPY vite.config.ts ./
COPY index.html ./

RUN npm run build

FROM nginx:1.25

COPY --from=build /var/www/tele-rps-gui/dist /usr/share/nginx/html

ENTRYPOINT ["nginx", "-g", "daemon off;"]
