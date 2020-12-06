package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Following
import com.get_your_music_5.social_system.resources.FollowingResource
import com.get_your_music_5.social_system.services.FollowingService
import com.get_your_music_5.users_system.services.MusicianService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class FollowingController(
        private val followingService: FollowingService,
        private val musicianService: MusicianService
) {

    @GetMapping("/followers/{followerId}/followed")
    fun getAllFollowedByFollowersId(@PathVariable followerId: Long) : ResponseEntity<List<FollowingResource>> {
        return try{
            musicianService.getById(followerId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val followings = followingService.getAllByFollowerId(followerId)
            if (followings.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(followings.map { following -> this.toResource(following) } , HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/followed/{followedId}/followers")
    fun getAllFollowersByFollowedId(@PathVariable followedId: Long) : ResponseEntity<List<FollowingResource>> {
        return try{
            musicianService.getById(followedId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val followings = followingService.getAllByFollowedId(followedId)
            if (followings.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(followings.map { following -> this.toResource(following) } , HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/followers/{followerId}/followed/{followedId}")
    fun create(@PathVariable followerId: Long,@PathVariable followedId: Long) : ResponseEntity<FollowingResource>{
        return try{
            val follower = musicianService.getById(followerId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val followed = musicianService.getById(followedId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val newFollowing = followingService.save(follower, followed)
            ResponseEntity(toResource(newFollowing), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/followers/{followerId}/followed/{followedId}")
    fun delete(@PathVariable followerId: Long,@PathVariable followedId: Long): ResponseEntity<*>{
        return try{
            musicianService.getById(followerId) ?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
            musicianService.getById(followedId) ?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
            followingService.delete(followerId, followedId)
            return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        } catch (e: Exception){
            ResponseEntity<Any>(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun toResource(entity: Following?) = FollowingResource(
            followerName = entity?.follower?.firstName,
            followedName = entity?.followed?.firstName,
            followDate = entity?.followDate
    )
}