dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dbCreate = "update"
    url = "jdbc:mysql://localhost:3306/socialconnect_2"
    dialect = org.hibernate.dialect.MySQL5InnoDBDialect

    username = "root"
    password = "root"

    properties {
        maxActive = 50
        maxIdle = 25
        minIdle = 5
        initialSize = 5
        minEvictableIdleTimeMillis = 60000
        timeBetweenEvictionRunsMillis = 60000
        maxWait = 10000
        validationQuery = "/* ping */"
    }
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {

}
