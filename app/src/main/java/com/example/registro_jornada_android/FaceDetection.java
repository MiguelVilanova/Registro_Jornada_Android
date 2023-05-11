package com.example.registro_jornada_android;

import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.*;

public class FaceDetection {

    // High-accuracy landmark detection and face classification
    FaceDetectorOptions highAccuracyOpts =
            new FaceDetectorOptions.Builder()
                    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                    .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                    .build();
}
