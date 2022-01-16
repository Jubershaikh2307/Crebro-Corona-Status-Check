package task.crebro.model

interface ParentListItem {
    fun getChildItemList():List<*>
    fun isInitiallyExpanded():Boolean
}