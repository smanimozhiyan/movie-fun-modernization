package org.superbiz.moviefun.org.superbiz.moviefun.albumsapi; /**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class AlbumsClient {
    private final Logger log = LoggerFactory.getLogger(AlbumsClient.class);
    private String albumsUrl;
    private RestOperations restOperations;

    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };

    public AlbumsClient(String albumsUrl, RestOperations restOperations) {
        this.albumsUrl = albumsUrl;
        this.restOperations = restOperations;
    }


    public void addAlbum(AlbumInfo album) {
        log.info("Adding album into the repository" + albumsUrl + album.toString());
        restOperations.postForEntity(albumsUrl, album, AlbumInfo.class);
    }

    public AlbumInfo find(long id) {
        log.info("Getting albums info using rest api call : URL : " + albumsUrl + "/" + id);
        AlbumInfo albumInfo = restOperations.getForObject(albumsUrl + "/" + id, AlbumInfo.class);
        log.info("Rest api call retrieved albums information : " + albumInfo);
        return albumInfo;
    }

    public HttpEntity getCover(long id) {
        log.info("Getting cover info using rest api call : URL : " + albumsUrl + "/" + id);
        HttpEntity coverInfo = restOperations.getForEntity(albumsUrl + "/" + id + "/cover", HttpEntity.class);
        log.info("Rest api call retrieved cover information : " + coverInfo);
        return coverInfo;
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.exchange(albumsUrl, GET, null, albumListType).getBody();
    }


    public void deleteAlbum(AlbumInfo album) {
        restOperations.delete(albumsUrl + "/" + album.getId());
    }


    public void updateAlbum(AlbumInfo album) {

//        entityManager.merge(album);
    }
}
