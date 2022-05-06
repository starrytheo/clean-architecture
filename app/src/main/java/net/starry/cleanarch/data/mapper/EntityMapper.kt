package net.starry.cleanarch.data.mapper

interface EntityMapper<in MODEL, out ENTITY> {
    fun mapFromRemote(model: MODEL): ENTITY
}
