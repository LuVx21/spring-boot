mvn clean package -Dmaven.test.skip=true \
&& docker compose build \
&& ((docker compose down && docker compose up -d) & (mvn clean))
