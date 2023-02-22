package com.example.jetreader.screens.PdfViewer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@Composable
fun PdfViewer(navController : NavController, pdfUri : String){

    val pdfState = rememberVerticalPdfReaderState(resource = ResourceType.Remote(pdfUri),
            isZoomEnable = true)

//   PDFReader(state = pdfState,
//          modifier = Modifier
//              .fillMaxSize()
//              .background(color = Color.LightGray))

}

//@Composable
//fun OpenPdf(uri : String){
// val rendererScope = rememberCoroutineScope()
//    val mutex = remember{ Mutex() }
//    val renderer by produceState<PdfRenderer?>(initialValue = null, producer = uri){
//        rendererScope.launch(Dispatchers.IO){
//            val input = ParcelFileDescriptor.open(uri.toFile())
//        }
//    }
//}













