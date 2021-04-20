package ren.imyan.a3dalbum

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

/**
 * @author EndureBlaze/炎忍 https://github.com/EndureBlaze
 * @data 2021-04-20 16:42
 * @website https://imyan.ren
 */
@Parcelize
data class Album(@DrawableRes val imageID: Int, @StringRes val titleID: Int) : Parcelable
