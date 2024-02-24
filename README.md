# Guide: text detect frame-processor-plugin With React Native Vision Camera to Detect Text from Camera Directly without image capture

As per the doc https://react-native-vision-camera.com/docs/guides/frame-processor-plugins there is a plugin aarongrider/vision-camera-ocr: A plugin to detect text in real time using MLKit Text Detector (OCR).

But its not working with React native 0.73.2 so finally decided to write custom plugin for my own.

## Here are the steps that you can follow. Android

#### Step 1: visit the url : Mostly automatic setup

https://react-native-vision-camera.com/docs/guides/frame-processors-plugins-android#mostly-automatic-setup

#### Step 2 : visit: https://developers.google.com/ml-kit/vision/text-recognition/v2

here you will get native code that you can write inside the files that is generated during step1

#### Step 3 : integrated with React Native Vision Camera

## Installation

Install my-project with npm

```bash
import React, {useRef, useEffect} from 'react';
import {StyleSheet, View} from 'react-native';
import {Button} from '@rneui/themed';

import {
  Camera,
  useCameraDevice,
  useFrameProcessor,
  VisionCameraProxy,
} from 'react-native-vision-camera';

const plugin = VisionCameraProxy.initFrameProcessorPlugin('imageTextDetector');

function imageTextDetector(frame) {
  'worklet';
  if (plugin == null) {
    throw new Error('Failed to load Frame Processor Plugin!');
  }
  return plugin.call(frame);
}

const CameraScreen = () => {
  const cameraRef = useRef(null);
  const device = useCameraDevice('back');

  const frameProcessor = useFrameProcessor(frame => {
    'worklet';
    console.log(
      `${frame.timestamp}: ${frame.width}x${frame.height} ${frame.pixelFormat} Frame (${frame.orientation})`,
    );
    // const scanResult = recognize(frame);
    const value = imageTextDetector(frame);

    // const data = scanOCR(frame);
    console.log('value:====== ', value);
  }, []);

  return (
    <View style={{flex: 1}}>
      <Camera
        ref={cameraRef}
        device={device}
        style={StyleSheet.absoluteFill}
        isActive={true}
        photo={true}
        frameProcessor={frameProcessor}
      />

      <Button title="Take photo" onPress={handleCamera} />
    </View>
  );
};

export default CameraScreen;

```

#### Step4 :

To make your task easy I uploaded two files
from location android/app/src/main/java/com/gemsessence/milkdairy that are generated at step 1

`Folder Name`: textdetectorframeprocessor on gihub code

## Authors

- [@VijayChouhan](https://github.com/vijaychouhan-rails)

## Feedback Or Hire Us

If you have any feedback or Hire Us, please reach out to us at vijay@gemsessence.com

## 🔗 Links

[![Upwork](https://w7.pngwing.com/pngs/257/806/png-transparent-upwork-freelancer-android-android-text-trademark-rectangle-thumbnail.png)](https://www.upwork.com/freelancers/~014bafb02b5f6b8ee2/)
