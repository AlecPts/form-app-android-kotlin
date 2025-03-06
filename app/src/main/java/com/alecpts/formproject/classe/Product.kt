package com.alecpts.formproject.classe

import android.graphics.Bitmap
import android.net.Uri
import java.io.Serializable


class Product(
    var type: String,
    var productName: String,
    var purchaseDate: String,
    var productColor: String,
    var productOrigin: String,
    var favorite: Boolean,
    var imagePainterId: Int? = null,
    var imageUri: Uri? = null,
    var imageBitmap: Bitmap? = null
): Serializable