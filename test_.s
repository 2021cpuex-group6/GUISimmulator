.global	min_caml_start
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
    mul x3 x1 x2  # x3 = 110
    addi x6 x0 20 
    sub x2 x2 x6 # x2 = -9
    mul x4 x2 x1 # x4 = -90
    div x3 x3 x1 # x3 = 11
    div x3 x3 x2 # x3 = -1
    addi x4 x0 17
    div x3 x4 x2 # x3 = -1
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
    fmul f10 f5 f4 # f10  6.125 
    fsw f7 -4(%x6) # 00 00 a8 40
    fsw f10 0(%x6) # 00 00 30 c1
