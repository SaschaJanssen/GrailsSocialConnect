import org.social.grails.customer.ContactType

import com.social.grails.security.Role
import com.social.grails.security.User
import com.social.grails.security.UserRole


class BootStrap {
    def springSecurityService

    def init = { servletContext ->

        // add initial content type data
        def contactType = ContactType.findById('FULL_NAME') ?: new ContactType(id: 'FULL_NAME').save(failOnError: true)
        contactType = ContactType.findById('POSTAL_ADDRESS') ?: new ContactType(id: 'POSTAL_ADDRESS').save(failOnError: true)
        contactType = ContactType.findById('EMAIL_ADDRESS') ?: new ContactType(id: 'EMAIL_ADDRESS').save(failOnError: true)
        contactType = ContactType.findById('WEB_ADDRESS') ?: new ContactType(id: 'WEB_ADDRESS').save(failOnError: true)
        contactType = ContactType.findById('FON_NUMBER') ?: new ContactType(id: 'FON_NUMBER').save(failOnError: true)
        contactType = ContactType.findById('MOBILE_NUMBER') ?: new ContactType(id: 'MOBILE_NUMBER').save(failOnError: true)

        // add initial user security data
        def userRole = Role.findByAuthority('ROLE_USER') ?: new Role(authority: 'ROLE_USER').save(failOnError: true)
        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)

        def adminUser = User.findByUsername('admin') ?: new User(
                        username: 'admin',
                        password: 'social',
                        enabled: true).save(failOnError: true)

        if (!adminUser.authorities.contains(adminRole)) {
            UserRole.create adminUser, adminRole
        }
    }

    def destroy = {
    }
}
