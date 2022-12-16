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
@aux7 DQ 1.0
@aux6 DB ?
_f20Main DQ ?
@aux5 DB ?
@aux4 DQ ?
@aux9 DQ -1.2
@aux20 DQ 30.0
@aux8 DQ 0.6
@aux21 DQ 6.0
_xxMainf20f20 DQ ?
_fiMainf1 DB ?
_zMainf1f2 DQ ?
_fiMain DB ?
_xMainf1 DB ?
_wMain DB ?
@aux3 DQ 50.0
@aux2 DQ 49.0
@aux1 DB "askjhdjla", 0
_f20Mainf20 DB ?
@aux0 DQ 9000.0
_pepitoMainf20f20 DQ ?
_zMainf1 DQ ?
_xMainf1f2 DB ?
_f2Mainf1 DB ?
_zMainf1fi DQ ?
_f1Main DB ?
_iMain DQ ?
_xxMainfi DQ ?
_xxMainf20 DQ ?
@aux11 DQ 2.0E34
@aux12 DQ 0.25
_xMainf1fi DB ?
@aux13 DQ 13.0
@aux14 DQ 0.0
_zMainfi DB ?
@aux10 DQ 3.0E-5
@aux19 DB ?
_yMain DB ?
_pepeMainf20 DB ?
@aux15 DQ 1.2E10
@aux16 DB ?
@aux17 DB ?
@aux18 DB ?
.CODE
START:
JMP Label_27
Label_1:
MOV EBX, 1
POP DX
POP AX
MOV _xMainf1, AL
PUSH DX
JMP Label_14
Label_6:
MOV EBX, 6
POP DX
POP AX
MOV _xMainf1f2, AL
PUSH DX
POP DX
MOV AL, _xMainf1f2
PUSH AX
PUSH DX
RET
Label_14:
JMP Label_23
Label_15:
MOV EBX, 15
POP DX
POP AX
MOV _xMainf1fi, AL
PUSH DX
POP DX
MOV AL, _xMainf1fi
PUSH AX
PUSH DX
RET
Label_23:
POP DX
MOV AL, _xMainf1
PUSH AX
PUSH DX
RET
Label_27:
FLD @aux0
FSTP _iMain
invoke MessageBox, NULL, addr @aux1, addr _out, MB_OK
MOV AL, 30
CMP AL, 20
JAE Label_33
FLD @aux2
FSTP _iMain
JMP Label_35
Label_33:
FLD @aux3
FLD @aux3
FADD
FSTP @aux4
FLD @aux4
FSTP _iMain
Label_35:
MOV AL, 30
CMP AL, 20
JAE Label_39
FLD @aux3
FSTP _iMain
JMP Label_39
MOV AL, 30
MOV _xMain, AL
Label_41:
CMP _xMain, 20
JAE Label_47
FLD @aux3
FSTP _iMain
MOV AL, 34
MOV @aux5, AL
MOV AL, _xMain
ADD AL, @aux5
MOV @aux6, AL
MOV AL, @aux6
MOV _xMain, AL
JMP Label_41
Label_47:
FLD @aux7
FSTP _iMain
FLD @aux8
FSTP _iMain
FLD @aux9
FSTP _iMain
FLD @aux10
FSTP _iMain
FLD @aux11
FSTP _iMain
FLD @aux12
FSTP _iMain
FLD @aux13
FSTP _iMain
FLD @aux14
FSTP _iMain
FLD @aux15
FSTP _iMain
MOV AL, 30
MOV _xMain, AL
Label_57:
CMP _xMain, 20
JAE Label_63
MOV AL, 40
MOV _xMain, AL
MOV AL, 34
MOV @aux16, AL
MOV AL, _xMain
ADD AL, @aux16
MOV @aux17, AL
MOV AL, @aux17
MOV _xMain, AL
JMP Label_57
Label_63:
MOV AL, 30
MOV _xMain, AL
Label_64:
CMP _xMain, 20
JAE Label_70
JMP Label_70
MOV AL, 34
MOV @aux18, AL
MOV AL, _xMain
ADD AL, @aux18
MOV @aux19, AL
MOV AL, @aux19
MOV _xMain, AL
JMP Label_64
Label_70:
JMP Label_80
Label_71:
MOV EBX, 71
POP DX
FSTP _xxMainfi
PUSH DX
FLD @aux20
FSTP _xxMainfi
POP DX
MOV AL, 4
PUSH AX
PUSH DX
RET
Label_80:
JMP Label_98
Label_81:
MOV EBX, 81
POP DX
FSTP _xxMainf20
PUSH DX
JMP Label_94
Label_86:
MOV EBX, 86
POP DX
FSTP _xxMainf20f20
PUSH DX
POP DX
MOV AL, _pepeMainf20
PUSH AX
PUSH DX
RET
Label_94:
POP DX
FLD @aux21
PUSH DX
RET
Label_39:
invoke MessageBox, NULL, addr @aux1, addr _out, MB_OK
Label_98:
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
