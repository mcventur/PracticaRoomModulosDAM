package com.mpd.pmdm.practicaroommodulos.core

import androidx.annotation.StringRes

class MandatoryFieldException(@StringRes val stringResError: Int) : IllegalArgumentException()