FROM haproxy 
COPY haproxy.cfg /usr/local/etc/haproxy/haproxy.cfg
COPY xip.io.pem /etc/ssl/xip.io/xip.io.pem
COPY keystore.pem /etc/ssl/certs/keystore.pem
RUN mkdir /run/haproxy
