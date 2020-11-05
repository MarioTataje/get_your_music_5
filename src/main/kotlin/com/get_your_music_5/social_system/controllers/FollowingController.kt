package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Following
import com.get_your_music_5.social_system.resources.FollowingResource
import com.get_your_music_5.social_system.services.FollowingService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class FollowingController(
        private val followingService: FollowingService
) {

    @GetMapping("/followers/{followerId}/followed")
    fun getAllFollowedByFollowersId(@PathVariable followerId: Long) : List<FollowingResource> {
        val followings = followingService.getAllByFollowerId(followerId)
        return toResourceList(followings)
    }

    @GetMapping("/followed/{followedId}/followers")
    fun getAllFollowersByFollowedId(@PathVariable followedId: Long) : List<FollowingResource> {
        val followings = followingService.getAllByFollowedId(followedId)
        return toResourceList(followings)
    }

    @PostMapping("/followers/{followerId}/followed/{followedId}")
    fun create(@PathVariable followerId: Long,@PathVariable followedId: Long) : FollowingResource{
        val newFollowing = followingService.save(followerId, followedId)
        return toResource(newFollowing)
    }

    @DeleteMapping("/followers/{followerId}/followed/{followedId}")
    fun delete(@PathVariable followerId: Long,@PathVariable followedId: Long){
        followingService.delete(followerId, followedId)
    }

    fun toResourceList(entities: List<Following>) : List<FollowingResource>{
        val resources = mutableListOf<FollowingResource>()
        for(entity in entities){
            val resource = FollowingResource(
                    followerName = entity.follower?.firstName,
                    followedName = entity.followed?.firstName,
                    followDate = entity.followDate
            )
            resources.add(resource)
        }
        return resources
    }

    fun toResource(entity: Following?) = FollowingResource(
            followerName = entity?.follower?.firstName,
            followedName = entity?.followed?.firstName,
            followDate = entity?.followDate
    )
}