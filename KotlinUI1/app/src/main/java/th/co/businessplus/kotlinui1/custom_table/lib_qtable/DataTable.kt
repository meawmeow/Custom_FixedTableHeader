package th.co.businessplus.kotlinui1.custom_table.lib_qtable

class DataTable {
    lateinit var type: String
    lateinit var data: Array<String>

    constructor(col1: String, col2: String, col3: String) {
        data = arrayOf(
            col1,
            col2,
            col3
        )
    }
    constructor(col1: String, col2: String, col3: String, col4: String) {
        data = arrayOf(
            col1,
            col2,
            col3,
            col4
        )
    }
    constructor(col1: String, col2: String, col3: String, col4: String, col5: String) {
        data = arrayOf(
            col1,
            col2,
            col3,
            col4,
            col5
        )
    }
    constructor(col1: String, col2: String, col3: String, col4: String, col5: String, col6: String) {
        data = arrayOf(
            col1,
            col2,
            col3,
            col4,
            col5,
            col6
        )
    }
    constructor(col1: String, col2: String, col3: String, col4: String, col5: String, col6: String, col7: String) {
        data = arrayOf(
            col1,
            col2,
            col3,
            col4,
            col5,
            col6,
            col7
        )
    }

    constructor(
        col1: String,
        col2: String,
        col3: String,
        col4: String,
        col5: String,
        col6: String,
        col7: String,
        col8: String
    ) {
        data = arrayOf(
            col1,
            col2,
            col3,
            col4,
            col5,
            col6,
            col7,
            col8
        )
    }
}