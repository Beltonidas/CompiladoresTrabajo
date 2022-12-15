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
@aux6 DB "Esta iria 7to", 0
@aux5 DB "Esta iria 6to", 0
@aux4 DB "Esta iria 5to", 0
_aMain DB ?
_fiMain DB ?
_xMainfi DB ?
@aux3 DB "Esta iria 4to", 0
@aux2 DB "Esta iria 3ero", 0
@aux1 DB "Esta iria 2da", 0
@aux0 DB "Esta iria 1ero", 0
.CODE
START:
MOV AL, 2
MOV _aMain, AL
invoke MessageBox, NULL, addr @aux0, addr _out, MB_OK
JMP Label_21
Label_3:
MOV EBX, 3
POP DX
POP AX
MOV _xMainfi, AL
PUSH DX
invoke MessageBox, NULL, addr @aux1, addr _out, MB_OK
PUSH EBX
CMP EBX, 3
JE ErrRec
MOV AL, 4
PUSH AX
CALL Label_3
POP AX
MOV _fiMain, AL
POP EBX
invoke MessageBox, NULL, addr @aux2, addr _out, MB_OK
invoke MessageBox, NULL, addr @aux3, addr _out, MB_OK
POP DX
MOV AL, _xMainfi
PUSH AX
PUSH DX
RET
Label_21:
PUSH EBX
CMP EBX, 3
JE ErrRec
MOV AL, 4
PUSH AX
CALL Label_3
POP AX
MOV _fiMain, AL
POP EBX
invoke MessageBox, NULL, addr @aux4, addr _out, MB_OK
invoke MessageBox, NULL, addr @aux5, addr _out, MB_OK
invoke MessageBox, NULL, addr @aux6, addr _out, MB_OK
JMP final
ErrRec:
invoke MessageBox, NULL, addr _ErrRec, addr _ErrRec, MB_OK
JMP final
ErrUiNeg:
invoke MessageBox, NULL, addr _ErrUiNeg, addr _ErrUiNeg, MB_OK
JMP final
ErrOvMul:
invoke MessageBox, NULL, addr _ErrOvMul, addr _ErrOvMul, MB_OK
JMP final
final:
END START
