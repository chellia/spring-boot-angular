package contracts.personresource

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/api/v1/persons'
        headers {
            contentType applicationJson()
        }
    }
    response {
        status 200
        headers {
            contentType applicationJson()
        }
    }
}