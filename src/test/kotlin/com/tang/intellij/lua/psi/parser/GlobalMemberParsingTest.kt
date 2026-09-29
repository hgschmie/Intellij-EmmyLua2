package com.tang.intellij.lua.psi.parser

import com.intellij.psi.PsiErrorElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.tang.intellij.lua.psi.LuaTypes

class GlobalMemberParsingTest : BasePlatformTestCase() {
    private fun assertParses(source: String) {
        val file = myFixture.configureByText("settings.lua", source)
        val errors = PsiTreeUtil.findChildrenOfType(file, PsiErrorElement::class.java)
        assertTrue(errors.joinToString { "${it.errorDescription} at ${it.textOffset}" }, errors.isEmpty())
    }

    fun testSettingsReadAndWrite() {
        assertParses("""
            local settings_table = {
                load_value = function(name) return settings.global[name] end,
                store_value = function(name, value) settings.global[name] = { value = value } end,
            }
        """.trimIndent())
        val offset = myFixture.file.text.indexOf("global")
        assertSame(LuaTypes.ID, myFixture.file.findElementAt(offset)!!.node.elementType)
    }

    fun testChainedMembersAndMethodCalls() {
        assertParses("local value = settings.global.global[name]; settings:global(name)")
    }

    fun testMemberFunctionDefinitions() {
        assertParses("function settings.global(name) return name end; function settings:global() end")
    }

    fun testNamedTableField() {
        assertParses("local settings = { global = {} }; return settings.global")
    }

    fun testLua55GlobalDeclarations() {
        assertParses("global value = 1; global function callback() end; global *")
        assertSame(LuaTypes.GLOBAL, myFixture.file.findElementAt(0)!!.node.elementType)
    }

    fun testOrdinaryKeywordsStillRejectedAsMembers() {
        val file = myFixture.configureByText("invalid.lua", "return settings.end[name]")
        assertFalse(PsiTreeUtil.findChildrenOfType(file, PsiErrorElement::class.java).isEmpty())
    }
}
