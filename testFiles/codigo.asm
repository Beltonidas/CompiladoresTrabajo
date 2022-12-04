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
_xMain DB ?
@aux7 DB ?
@aux6 DB ?
@aux5 DB ?
@aux4 DB ?
@aux9 DB ?
@aux8 DB ?
_iMain DB ?
_aMain DB ?
_fiMain DB ?
_xMainfi DB ?
_zMain DQ ?
@aux3 DB ?
@aux2 DB ?
_jMain DB ?
@aux1 DB ?
@aux0 DQ 10.0
@aux11 DQ 3.0
_bMain DB ?
@aux12 DB "askjhdjla", 0
_zMainfi DQ ?
@aux10 DB ?
_vMain DQ ?
.CODE
START:
FILD @aux0
FSTP _zMain
MOV AL, 30
CMP AL, 20
JGE Label_5
MOV AL, 49
MOV _iMain, AL
JMP Label_7
Label_5:
MOV AL, 100
MOV @aux1, AL
MOV AL, 50
MOV @aux2, AL
MOV AL, @aux2
ADD AL, @aux1
MOV @aux3, AL
MOV AL, @aux3
MOV _iMain, AL
Label_7:
JMP Label_22
Label_8:
MOV EBX, 8
POP AX
MOV _xMainfi, AL
PUSH EBX
CMP EBX, 8
JE ErrRec
MOV AL, _xMainfi
PUSH AX
CALL Label_8
POP AX
MOV _fiMain, AL
POP EBX
FILD @aux0
FSTP _zMainfi
MOV AL, _aMain
MUL _bMain
MOV @aux4, AL
MOV AL, @aux4
MOV _aMain, AL
MOV AL, _xMainfi
PUSH AX
Label_22:
MOV AL, 10
MOV _aMain, AL
PUSH EBX
CMP EBX, 8
JE ErrRec
MOV AL, _aMain
PUSH AX
CALL Label_8
POP AX
MOV _fiMain, AL
POP EBX
MOV AL, 10
MOV _xMain, AL
MOV AL, 0
MOV _iMain, AL
Label_32:
CMP _iMain, 10
JGE Label_51
JMP Label_48
MOV AL, 0
MOV _jMain, AL
Label_36:
CMP _jMain, 10
JGE Label_43
JMP Label_40
JMP Label_51
Label_40:
MOV AL, 1
MOV @aux5, AL
MOV AL, _jMain
ADD AL, @aux5
MOV @aux6, AL
MOV AL, @aux6
MOV _jMain, AL
JMP Label_36
Label_43:
MOV AL, 2
MOV @aux7, AL
MOV AL, _aMain
MUL @aux7
MOV @aux8, AL
MOV AL, @aux8
MOV _aMain, AL
JMP Label_51
MOV AL, _xMain
MOV _bMain, AL
MOV AL, _aMain
MOV _xMain, AL
Label_48:
MOV AL, 1
MOV @aux9, AL
MOV AL, _iMain
ADD AL, @aux9
MOV @aux10, AL
MOV AL, @aux10
MOV _iMain, AL
JMP Label_32
Label_51:
FILD @aux11
FSTP _zMain
FILD @aux11
FSTP _vMain
invoke MessageBox, NULL, addr @aux12, addr _out, MB_OK
PUSH EBX
CMP EBX, 8
JE ErrRec
MOV AL, _aMain
PUSH AX
CALL Label_8
POP AX
MOV _fiMain, AL
POP EBX
MOV AL, _fiMain
MOV _xMain, AL
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
