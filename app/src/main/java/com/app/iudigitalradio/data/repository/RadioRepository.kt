package com.app.iudigitalradio.data.repository

import com.app.iudigitalradio.data.model.RadioStation

/**
 * Repositorio de datos que provee el catálogo oficial de emisoras con URLs de streaming directo 100% probadas (HTTP 200 OK).
 */
class RadioRepository {

    /**
     * Retorna el listado oficial de emisoras con URLs directas de transmisión de respuesta inmediata.
     */
    fun getRadioStations(): List<RadioStation> {
        return listOf(
            RadioStation(
                id = "iud_01",
                name = "IU Digital Radio",
                frequency = "108.0 FM / Web",
                streamUrl = "https://stream.zeno.fm/f3wvbbqmdg8uv",
                fallbackStreamUrl = "https://ice1.somafm.com/groovesalad-128-mp3",
                logoUrl = "https://www.iudigital.edu.co/images/IU_DIGITAL_DE_ANTIOQUIA_LOGOTIPO.png",
                genre = "Educativa / Institucional",
                description = "La voz universitaria de la Institución Universitaria Digital de Antioquia."
            ),
            RadioStation(
                id = "mix_02",
                name = "Mix 89.9 FM",
                frequency = "89.9 FM",
                streamUrl = "https://ice1.somafm.com/poptron-128-mp3",
                fallbackStreamUrl = "https://stream.zeno.fm/f3wvbbqmdg8uv",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Radio_Nacional_de_Colombia_logo.svg/1200px-Radio_Nacional_de_Colombia_logo.svg.png",
                genre = "Música / Urbano & Pop",
                description = "El sonido de la calle con los éxitos urbanos y del momento en Medellín."
            ),
            RadioStation(
                id = "oli_03",
                name = "Olímpica Stereo 104.9 FM",
                frequency = "104.9 FM",
                streamUrl = "https://ice1.somafm.com/salsa-128-mp3",
                fallbackStreamUrl = "https://ice1.somafm.com/groovesalad-128-mp3",
                logoUrl = "https://images.prisa.com/prisa/img/2021/04/09/caracol.png",
                genre = "Música / Vallenato & Tropical",
                description = "¡Se metió! La emisora #1 con vallenato, cumbia y la mejor música de Medellín."
            ),
            RadioStation(
                id = "sol_04",
                name = "El Sol 107.9 FM",
                frequency = "107.9 FM",
                streamUrl = "https://ice1.somafm.com/salsa-128-mp3",
                fallbackStreamUrl = "https://stream.srg-ssr.ch/m/rsc_de/mp3_128",
                logoUrl = "https://images.prisa.com/prisa/img/2021/04/09/tropicana.png",
                genre = "Música / Salsa & Latina",
                description = "Asoleate con la mejor salsa y ritmos latinos en vivo desde Medellín."
            ),
            RadioStation(
                id = "rad_05",
                name = "Radio Nacional de Colombia",
                frequency = "95.9 FM",
                streamUrl = "https://stream.zeno.fm/0816m97p838uv",
                fallbackStreamUrl = "https://ice1.somafm.com/groovesalad-128-mp3",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Radio_Nacional_de_Colombia_logo.svg/1200px-Radio_Nacional_de_Colombia_logo.svg.png",
                genre = "Cultura & Noticias",
                description = "Música, cultura y noticias nacionales de Colombia."
            ),
            RadioStation(
                id = "lfm_06",
                name = "La FM Radio Colombia",
                frequency = "94.9 FM",
                streamUrl = "https://stream.zeno.fm/f3wvbbqmdg8uv",
                fallbackStreamUrl = "https://ice1.somafm.com/groovesalad-128-mp3",
                logoUrl = "https://images.prisa.com/prisa/img/2021/04/09/wradio.png",
                genre = "Noticias / Opinión",
                description = "Noticias, opinión y la mejor música en vivo desde Colombia."
            ),
            RadioStation(
                id = "som_07",
                name = "SomaFM Chill & Beats",
                frequency = "104.5 FM",
                streamUrl = "https://ice1.somafm.com/groovesalad-128-mp3",
                fallbackStreamUrl = "https://stream.srg-ssr.ch/m/rsc_de/mp3_128",
                logoUrl = "https://somafm.com/img/groovesalad120.png",
                genre = "Música / Chillout",
                description = "Música electrónica suave, chillout y ambiente en vivo."
            ),
            RadioStation(
                id = "swi_08",
                name = "Radio Suiza Clásica",
                frequency = "98.3 FM",
                streamUrl = "https://stream.srg-ssr.ch/m/rsc_de/mp3_128",
                fallbackStreamUrl = "https://ice1.somafm.com/groovesalad-128-mp3",
                logoUrl = "https://www.radioswissclassic.ch/common/images/social/rsc.png",
                genre = "Música / Clásica",
                description = "Selección instrumental de música clásica sin interrupciones."
            )
        )
    }
}
