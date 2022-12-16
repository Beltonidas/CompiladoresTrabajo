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
@aux5 DB "sigue la ejecucion normalmente", 0
@aux4 DB ?
@aux9 DB ?
@aux20 DB ?
@aux8 DB ?
@aux21 DB ?
_aMain DB ?
_fiMain DB ?
@aux3 DB ?
@aux2 DB ?
@aux1 DB ?
@aux0 DQ 10.0
_iMain DB ?
_xMainfi DB ?
_zMain DQ ?
_jMain DB ?
@aux11 DB ?
_bMain DB ?
@aux12 DQ 3.0
@aux13 DB "askjhdjla", 0
@aux14 DB ?
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
FLD @aux0
FSTP _zMain
MOV AL, 30
CMP AL, 20
JAE Label_6
MOV AL, 49
MOV _iMain, AL
JMP Label_8
Label_6:
MOV AL, 100
MOV @aux1, AL
MOV AL, 50
MOV @aux2, AL
MOV AL, @aux2
ADD AL, @aux1
MOV @aux3, AL
MOV AL, @aux3
MOV _iMain, AL
Label_8:
JMP Label_20
Label_9:
MOV EBX, 8
POP DX
POP AX
MOV _xMainfi, AL
PUSH DX
FLD @aux0
FSTP _zMainfi
MOV AL, _aMain
MUL _bMain
MOV @aux4, AL
MOV AL, @aux4
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
CMP EBX, 9
JE ErrRec
MOV AL, _aMain
PUSH AX
CALL Label_9
POP AX
MOV _fiMain, AL
POP EBX
MOV AL, 10
MOV _xMain, AL
invoke MessageBox, NULL, addr @aux5, addr _out, MB_OK
MOV AL, 0
MOV _iMain, AL
Label_43:
CMP _iMain, 10
JAE Label_62
JMP Label_59
MOV AL, 0
MOV _jMain, AL
Label_48:
CMP _jMain, 10
JAE Label_55
JMP Label_52
Label_52:
JMP Label_62
MOV AL, 1
MOV @aux6, AL
MOV AL, _jMain
ADD AL, @aux6
MOV @aux7, AL
MOV AL, @aux7
MOV _jMain, AL
JMP Label_48
Label_55:
MOV AL, 2
MOV @aux8, AL
MOV AL, _aMain
MUL @aux8
MOV @aux9, AL
MOV AL, @aux9
MOV _aMain, AL
JMP Label_62
MOV AL, _xMain
MOV _bMain, AL
Label_59:
MOV AL, _aMain
MOV _xMain, AL
MOV AL, 1
MOV @aux10, AL
MOV AL, _iMain
ADD AL, @aux10
MOV @aux11, AL
MOV AL, @aux11
MOV _iMain, AL
JMP Label_43
Label_62:
FLD @aux12
FSTP _zMain
FLD @aux12
FSTP _vMain
invoke MessageBox, NULL, addr @aux13, addr _out, MB_OK
PUSH EBX
CMP EBX, 9
JE ErrRec
MOV AL, _aMain
PUSH AX
CALL Label_9
POP AX
MOV _fiMain, AL
POP EBX
MOV AL, 5
MOV @aux14, AL
MOV AL, 3
MOV @aux15, AL
MOV AX, 0
MOV AL, @aux15
DIV @aux14
MOV @aux16, AL
MOV AL, 8
MOV @aux17, AL
MOV AX, 0
MOV AL, @aux16
DIV @aux17
MOV @aux18, AL
MOV AL, _fiMain
ADD AL, @aux18
MOV @aux19, AL
MOV AL, 0
MOV @aux20, AL
MOV AL, @aux19
SUB AL, @aux20
CMP AL, 0
JL ErrUiNeg
MOV @aux21, AL
MOV AL, @aux21
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
