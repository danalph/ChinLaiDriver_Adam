package addam.com.my.chinlaicustomer.feature.invoice

import addam.com.my.chinlaicustomer.rest.model.Invoices

/**
 * Created by owner on 26/03/2019
 */
data class InvoiceMonthModel (
    val month: String,

    val items: List<Invoices>
)