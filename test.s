# テスト用
.global	min_caml_start
start:
    jal x4 test # x4は5行目
    addi x1 x1 1000
    nop
    jr  4(%x2) # 12行目に飛ぶ
    jalr x2 -16(%x3) # 2つ上に飛ぶ x2は8行目
test:
    jalr x3 12(%x4) # 8行目に飛ぶ x3は12行目
test2:
    addi x1 x0 0 # x1 = 0
    addi x4 x0 4 # x4 = 4
    addi x2 x0 520  # x2 = 512 + 8 10 00001000
    addi x3 x0 -1 # x3 = -1
    nop
    sw x2 8(x1) # 8... 10 00001000
    sw x3 -4(x4) # 0... 11111...
    lw x4 4(x4) # x4 = 520
    lw x1 0(x1) # x1 = -1
    addi x1 x0 10
    addi x2 x0 11
    addi x10 x0 2
    addi x1  x0 24
    addi x2 x0 -8
    sra %x3 %x2 x10
    sra %x4 %x1 x10 
    srl %x5 %x2 x10
    srl %x6 %x1 x10
    addi x1 x0 21 # シフト数
    addi %x2 x0 1532 # x2 1 0111 1111 00
    addi x3 %x0 516 # x3 0 1000 0001 00
    addi x4 x0 1543 # x4 1 1000 0001 11
    addi x5 x0 1531 # x5 1 0111 1110 11
    sll x2 x2 %x1 # x2 = -1
    sll x3 %x3 x1 # x3 = 4
    sll x4 x4 x1 # x4 = -7
    sll x5 x5 x1 # x5 = -0.75
    addi x6 x0 20 # ベースレジスタ
    sw x2 -4(x6)
    sw x3 0(%x6)
    sw %x4 4(x6)
    sw %x5 8(x6)
    flw %f2 -4(%x6) # f2 -1
    flw %f3 0(x6)  # f3 4
    flw f4 4(x6)  # f4 -7
    flw f5 8(x6)  # f5 -0.875
    fadd f6 f2 f3 # f6 3 
    fsub f7 f4 f3 # f7 -11
    fadd f8 f5 f3 # f8 3.125
    fmul f9 f3 f4 # f9 -28
    fmv f10 f9 # f10 -28
    fmul f10 f5 f4 # f10  6.125 
    floor f11 f10 # f11  6.00
    floor f12 f5 # f12 -1
    ftoi x10 f12 # x10 -1
    ftoi x11 f5 # x11 -1
    ftoi x12 f8 # x12 3
    addi x1 x0 23
    addi x2 x0 -431
    addi x3 x0 1023
    itof f13 x1 
    itof f14 x2
    itof f15 x3
    itof f16 x0
    fsw f7 -4(%x6) # 00 00 30 c1
    fsw f10 0(%x6) # 00 00 c4 40
    addi x1 x0 1
    addi x2 x0 20
    sll x1 x1 x2
    addi x2 x0 0
loop1: # 2^20 回繰り返す
    add x3 x2 x0 
    addi x4 x0 3
    itof f1 x4
    itof f2 x3
    fadd f3 f1 f2
    fmul f4 f1 f2
    fsqrt f3 f3
    sll x3 x3 x2
    sw x2 0(x0)
    lw x3 0(x0)
    addi x2 x2 1
    blt x2 x1 loop1
