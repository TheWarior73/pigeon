package com.github.thewarior73.pigeon.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.thewarior73.pigeon.R
import com.github.thewarior73.pigeon.data.model.BureauPoste
import com.github.thewarior73.pigeon.ui.theme.blue_laposte
import com.github.thewarior73.pigeon.ui.theme.creamy_white
import com.github.thewarior73.pigeon.ui.theme.gray_laposte_900
import com.github.thewarior73.pigeon.ui.theme.yellow_laposte_200

@Composable
fun BureauPosteItem(
    poste: BureauPoste,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        Surface(
            color = yellow_laposte_200,
            modifier = Modifier.fillMaxWidth()
        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.laposte),
                contentDescription = "La poste",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = poste.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                    text = "${poste.distance} km",
                    style = MaterialTheme.typography.titleSmall
                )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_alarm_24),
                        contentDescription = "Horaire",
                        tint = blue_laposte,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${poste.horaire}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = blue_laposte
                    )
                }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.CenterEnd

                    ) {
                        Text(
                            text = "detail",
                            style = MaterialTheme.typography.bodySmall,
                            color = creamy_white
                        )

                }
            }
        }
        }
    }
}
