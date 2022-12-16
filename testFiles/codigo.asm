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
@aux20 DB ?
@aux8 DB ?
@aux21 DQ 3.0
_aMain DB ?
_fiMain DB ?
@aux3 DB ?
@aux2 DB ?
@aux1 DQ 10.0
@aux0 DB "askjhdjla", 0
_iMain DB ?
_xMainfi DB ?
_zMain DQ ?
_jMain DB ?
@aux11 DB ?
_bMain DB ?
@aux12 DB ?
@aux13 DB ?
@aux14 DB "sigue la ejecucion normalmente", 0
_zMainfi DQ ?
@aux10 DB ?
@aux19 DB ?
_vMain DQ ?
@aux15 DB ?
@aux16 DB ?
@aux17 DB ?
@aux18 DB ?
.CODE
START:
invoke MessageBox, NULL, addr @aux0, addr _out, MB_OK
FLD @aux1
FSTP _zMain
MOV AL, 30
CMP AL, 20
JAE Label_6
MOV AL, 49
MOV _iMain, AL
JMP Label_8
Label_6:
MOV AL, 100
MOV @aux2, AL
MOV AL, 50
MOV @aux3, AL
MOV AL, @aux3
ADD AL, @aux2
MOV @aux4, AL
MOV AL, @aux4
MOV _iMain, AL
Label_8:
JMP Label_20
MOV EBX, 8
POP DX
POP AX
MOV _xMainfi, AL
PUSH DX
FLD @aux1
FSTP _zMainfi
MOV AL, _aMain
MUL _bMain
MOV @aux5, AL
MOV AL, @aux5
MOV _aMain, AL
POP DX
MOV AL, _xMainfi
PUSH AX
PUSH DX
RET
Label_20:
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
PUSH EBX
CMP EBX, 8
JE ErrRec
MOV AL, _aMain
PUSH AX
CALL Label_8
POP AX
MOV _fiMain, AL
POP EBX
MOV AL, 5
MOV @aux6, AL
MOV AL, 3
MOV @aux7, AL
MOV AX, 0
MOV AL, @aux7
DIV @aux6
MOV @aux8, AL
MOV AL, 8
MOV @aux9, AL
MOV AX, 0
MOV AL, @aux8
DIV @aux9
MOV @aux10, AL
MOV AL, _fiMain
ADD AL, @aux10
MOV @aux11, AL
MOV AL, 0
MOV @aux12, AL
MOV AL, @aux11
SUB AL, @aux12
CMP AL, 0
JL ErrUiNeg
MOV @aux13, AL
MOV AL, @aux13
MOV _xMain, AL
invoke MessageBox, NULL, addr @aux14, addr _out, MB_OK
MOV AL, 0
MOV _iMain, AL
Label_43:
CMP _iMain, 10
JAE Label_62
MOV AL, 1
MOV @aux15, AL
MOV AL, _iMain
ADD AL, @aux15
MOV @aux16, AL
JMP Label_59
MOV AL, 0
MOV _jMain, AL
Label_48:
CMP _jMain, 10
JAE Label_55
MOV AL, 1
MOV @aux17, AL
MOV AL, _jMain
ADD AL, @aux17
MOV @aux18, AL
JMP Label_52
Label_52:
JMP Label_62
MOV AL, @aux18
MOV _jMain, AL
JMP Label_48
Label_55:
MOV AL, 2
MOV @aux19, AL
MOV AL, _aMain
MUL @aux19
MOV @aux20, AL
MOV AL, @aux20
MOV _aMain, AL
JMP Label_62
MOV AL, _xMain
MOV _bMain, AL
Label_59:
MOV AL, _aMain
MOV _xMain, AL
MOV AL, @aux16
MOV _iMain, AL
JMP Label_43
Label_62:
FLD @aux21
FSTP _zMain
FLD @aux21
FSTP _vMain
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
