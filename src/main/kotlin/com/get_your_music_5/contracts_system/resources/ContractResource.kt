package com.get_your_music_5.contracts_system.resources

data class SaveContractResource(
        val name: String,
        val address: String,
        val reference: String,
        val startDate: String,
        val endDate: String,
        val districtId: Long
)

data class ContractResource(
        val id: Long?,
        val name: String,
        val address: String,
        val reference: String,
        val startDate: String,
        val endDate: String,
        val organizerName: String?,
        val musicianName: String?,
        val districtName: String?,
        val state: String?
)