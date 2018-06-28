package com.demo.rest.service;

import org.springframework.stereotype.Service;

@Service
public class RestTrackServiceImpl implements RestTrackService {
    @Override
    public void restTrack(String url, Object[] args, Object result, int resultCode) {
        System.out.println(url);
    }
}
