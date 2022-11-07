package io.billie.organisations.service

import java.util.UUID

class UnableToFindOrganisation(val id: UUID)
    : RuntimeException("Could not find organisation with ID $id")
