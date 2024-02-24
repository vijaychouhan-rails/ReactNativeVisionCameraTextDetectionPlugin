package com.gemsessence.milkdairy.textdetectorframeprocessor

import android.util.Log
import com.mrousavy.camera.frameprocessor.Frame
import com.mrousavy.camera.frameprocessor.VisionCameraProxy
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin

private fun getRotationDegreeFromOrientation(orientationString: String): Int {
    return when (orientationString) {
        "portrait" -> 90
        "landscape-left" -> 0
        "landscape-right" -> 270
        "portrait-upside-down" -> 180
        else -> 0 // Handle invalid string values
    }
}

class TextDetectorFrameProcessorPlugin(proxy: VisionCameraProxy, options: Map<String, Any>?): FrameProcessorPlugin() {
    override fun callback(frame: Frame, params: Map<String, Any>?): Any? {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val mediaImage = frame.image
        val result = hashMapOf<String, Any>()

        if (mediaImage != null) {
            val orientation = frame.orientation
            val rotationDegrees = getRotationDegreeFromOrientation(orientation.toString())

            val image = InputImage.fromMediaImage(mediaImage, rotationDegrees)
            val task: Task<Text> = recognizer.process(image)
            try {
                val text: Text = Tasks.await<Text>(task)
                result["text"] = text.text // listOf(text.text) // Convert text to a list
                result["blocks"] = getBlockArray(text.textBlocks) // Add blocks list
            } catch (e: Exception) {
                Log.e("TextDetector", "Error processing text: ${e.message}", e)
                return null
            }
        }

        return result
    }

    private fun getBlockArray(blocks: MutableList<Text.TextBlock>): List<Map<String, String>> {
        return blocks.map { block ->
            mapOf("text" to block.text) // Extract desired data from blocks
        }
    }
}




// package com.gemsessence.milkdairy.textdetectorframeprocessor

// import android.util.Log
// import com.mrousavy.camera.frameprocessor.Frame
// import com.mrousavy.camera.frameprocessor.VisionCameraProxy
// // import com.facebook.react.bridge.Arguments
// // import com.facebook.react.bridge.WritableArray
// // import com.facebook.react.bridge.WritableNativeArray
// // import com.facebook.react.bridge.WritableNativeMap
// import com.google.android.gms.tasks.Task
// import com.google.android.gms.tasks.Tasks
// import com.google.mlkit.vision.common.InputImage
// import com.google.mlkit.vision.text.Text
// import com.google.mlkit.vision.text.TextRecognition
// import com.google.mlkit.vision.text.TextRecognizer
// import com.google.mlkit.vision.text.latin.TextRecognizerOptions
// import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin
// import com.mrousavy.camera.types.Orientation;

// class TextDetectorFrameProcessorPlugin(proxy: VisionCameraProxy, options: Map<String, Any>?): FrameProcessorPlugin() {
//     override fun callback(frame: Frame, params: Map<String, Any>?): Any? {
        
//         val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
//         val mediaImage = frame.image
//         val result = hashMapOf<String, Any>()

//         if (mediaImage != null) {
//             val orientation = frame.orientation
//             val rotationDegrees = getRotationDegreeFromOrientation(orientation)

//             val image = InputImage.fromMediaImage(mediaImage, rotationDegrees)
//             val task: Task<Text> = recognizer.process(image)
//             try {
//                 val text: Text = Tasks.await<Text>(task)
//                 val keyName = "text"
//                 result[keyName] = text.text // PLEASE CONVERT TO LIST
//                 // result?.putArray("blocks", getBlockArray(text.textBlocks)) // PLEASE 
//             } catch (e: Exception) {
//                 return null
//             }
//         }

//         // val data = WritableNativeMap()
//         // data.putMap("result", result) // PLEASE CONVERT TO LIST
//         // val resultMap = HashMap<String, Object>()
//         // // Assuming result is a WritableMap (adjust based on actual type)
//         // for (key in (result as WritableMap).keySet()) {
//         //   resultMap[key] = (result as WritableMap)[key]
//         // }
        
//         return result

//     }
    
//     // private fun getRotationDegreeFromOrientation(orientation: Orientation): Int {
//     //     return when (orientation) {
//     //         Orientation.PORTRAIT -> 90
//     //         Orientation.LANDSCAPE_LEFT -> 0
//     //         Orientation.LANDSCAPE_RIGHT -> 270
//     //         Orientation.PORTRAIT_UPSIDE_DOWN -> 180
//     //     }
//     // }

//     // private fun getRotationDegreeFromOrientation(orientationString: String): Int {
//     // return when (orientationString) {
//     //     Orientation.PORTRAIT.getUnionValue() -> 90
//     //     Orientation.LANDSCAPE_LEFT.getUnionValue() -> 0
//     //     Orientation.LANDSCAPE_RIGHT.getUnionValue() -> 270
//     //     Orientation.PORTRAIT_UPSIDE_DOWN.getUnionValue() -> 180
//     //     else -> 0
//     // }
//     private fun getRotationDegreeFromOrientation(orientationString: String): Int {
//     return when (orientationString) {
//         "portrait" -> 90
//         "landscape-left" -> 0
//         "landscape-right" -> 270
//         "portrait-upside-down" -> 180
//         else -> 0 // Handle invalid string values
//       }
//     }
// }

//       //   private fun getBlockArray(blocks: MutableList<Text.TextBlock>): WritableNativeArray {
//       //     val blockArray = WritableNativeArray()

//       //     for (block in blocks) {
//       //         val blockMap = WritableNativeMap()

//       //         blockMap.putString("text", block.text)
//       //         blockArray.pushMap(blockMap)
//       //     }
//       //     return blockArray
//       // }

    
// }
