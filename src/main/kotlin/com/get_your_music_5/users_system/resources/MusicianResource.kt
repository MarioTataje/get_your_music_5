package com.get_your_music_5.users_system.resources

class MusicianResource(id: Long?,
                        firstName: String,
                        lastName: String,
                        birthDate: String,
                        phone: String,
                        description: String,
                        registerDate: String,
                        photoUrl: String,
                        type: String,
                        userEmail: String?,
                        districtName: String?,
                       val rating: Double
):
        ProfileResource(id, firstName, lastName, birthDate, phone, description,
                registerDate, photoUrl, type, userEmail, districtName)