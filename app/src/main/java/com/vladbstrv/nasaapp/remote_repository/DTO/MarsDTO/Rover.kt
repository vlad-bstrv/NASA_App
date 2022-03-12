package com.vladbstrv.nasaapp.remote_repository.DTO.MarsDTO

data class Rover(
    val id: Int,
    val landing_date: String,
    val launch_date: String,
    val name: String,
    val status: String
)