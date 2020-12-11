package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Following
import com.get_your_music_5.social_system.resources.FollowingResource
import com.get_your_music_5.social_system.services.FollowingService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class FollowingController(
        private val followingService: FollowingService
) {

    @GetMapping("/followers/{followerId}/followed")
    fun getAllFollowedByFollowersId(@PathVariable followerId: Long) : ResponseEntity<List<FollowingResource>> {
        val followings = followingService.getAllByFollowerId(followerId)
        if (followings.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(followings.map { following -> this.toResource(following) } , HttpStatus.OK)
    }

    @GetMapping("/followed/{followedId}/followers")
    fun getAllFollowersByFollowedId(@PathVariable followedId: Long) : ResponseEntity<List<FollowingResource>> {
        val followings = followingService.getAllByFollowedId(followedId)
        if (followings.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(followings.map { following -> this.toResource(following) } , HttpStatus.OK)
    }

    @PostMapping("/followers/{followerId}/followed/{followedId}")
    fun create(@PathVariable followerId: Long,@PathVariable followedId: Long) : ResponseEntity<FollowingResource>{
        val newFollowing = followingService.save(followerId, followedId)
        return ResponseEntity(toResource(newFollowing), HttpStatus.OK)
    }

    @DeleteMapping("/followers/{followerId}/followed/{followedId}")
    fun delete(@PathVariable followerId: Long,@PathVariable followedId: Long): ResponseEntity<*>{
        followingService.delete(followerId, followedId)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }

    fun toResource(entity: Following?) = FollowingResource(
            followerName = entity?.follower?.firstName,
            followedName = entity?.followed?.firstName,
            followDate = entity?.followDate
    )
}