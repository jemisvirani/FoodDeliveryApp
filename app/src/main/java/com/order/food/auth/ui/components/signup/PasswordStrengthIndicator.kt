package com.order.food.auth.ui.components.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.order.food.R;

@Composable
fun PasswordStrengthIndicator(
    strength: PasswordStrength
) {

    val progress = when (strength) {

        PasswordStrength.NONE -> 0
        PasswordStrength.WEAK -> 1
        PasswordStrength.MEDIUM -> 2
        PasswordStrength.STRONG -> 3

    }

    Column {

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            repeat(3) { index ->

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .background(
                            if (index < progress)
                                colorResource(R.color.buttonColor)
                            else
                                MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.shapes.small
                        )
                )

            }

        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = strength.name.lowercase()
                .replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodySmall,
            color = colorResource(R.color.buttonColor)
        )

        Spacer(Modifier.height(5.dp))


    }

}