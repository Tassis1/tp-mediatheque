FROM httpd:2.4

COPY ./mediatheque/ /usr/local/apache2/htdocs/

COPY ./ssl/ /usr/local/apache2/conf/ssl/

COPY ./mediatheque.conf /usr/local/apache2/conf/extra/mediatheque.conf

RUN sed -i \
    -e 's/^#LoadModule rewrite_module/LoadModule rewrite_module/' \
    -e 's/^#LoadModule ssl_module/LoadModule ssl_module/' \
    -e 's/^#LoadModule socache_shmcb_module/LoadModule socache_shmcb_module/' \
    -e 's/^#LoadModule headers_module/LoadModule headers_module/' \
    -e 's/^#LoadModule proxy_module/LoadModule proxy_module/' \
    -e 's/^#LoadModule proxy_http_module/LoadModule proxy_http_module/' \
    /usr/local/apache2/conf/httpd.conf

RUN echo "Include conf/extra/mediatheque.conf" >> /usr/local/apache2/conf/httpd.conf

EXPOSE 80 443

CMD ["httpd-foreground"]