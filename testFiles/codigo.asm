.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
_ErrRec db "Error recursion", 0
_ErrUiNeg db "Error resta resultado negativo para ui8", 0
_ErrOvMul db "Error overflow en mul de f64", 0
_dobleMax DQ 1.7976931348623157E308
_dobleMin DQ 2.2250738585072014E-308
_out db "Mensaje de Programa", 0
_variable_dosMain DB ?
_zMainfi DQ ?
_variable_unoMain DB ?
_variable_tresMain DB ?
_xMainfi DB ?
_fiMain DB ?
@aux1 DB ?
@aux0 DQ 10.0
.CODE
START:
JMP Label_15
Label_1:
MOV EBX, 1
POP AX
MOV _xMainfi, AL
PUSH EBX
CMP EBX, 1
JE ErrRec
MOV AL, _xMainfi
PUSH AX
CALL Label_1
POP AX
MOV _fiMain, AL
POP EBX
FILD @aux0
FSTP _zMainfi
MOV AL, _variable_unoMain
MUL _variable_dosMain
MOV @aux1, AL
MOV AL, @aux1
MOV _variable_unoMain, AL
MOV AL, _variable_unoMain
PUSH AX
Label_15:
MOV AL, 30
MOV _variable_unoMain, AL
MOV AL, 30
MOV _variable_dosMain, AL
MOV AL, 40
MOV _variable_tresMain, AL
PUSH EBX
CMP EBX, 1
JE ErrRec
MOV AL, _variable_tresMain
PUSH AX
CALL Label_1
POP AX
MOV _fiMain, AL
POP EBX
JMP final
ErrRec:
invoke MessageBox, NULL, addr _ErrRec, addr _ErrRec, MB_OK
invoke ExitProcess, 0
JMP final
ErrUiNeg:
invoke MessageBox, NULL, addr _ErrUiNeg, addr _ErrUiNeg, MB_OK
invoke ExitProcess, 0
JMP final
ErrOvMul:
invoke MessageBox, NULL, addr _ErrOvMul, addr _ErrOvMul, MB_OK
invoke ExitProcess, 0
JMP final
final:
END START
