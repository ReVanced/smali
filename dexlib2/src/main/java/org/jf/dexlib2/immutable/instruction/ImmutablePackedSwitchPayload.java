/*
 * Copyright 2012, Google Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jf.dexlib2.immutable.instruction;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.SwitchElement;
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ImmutablePackedSwitchPayload extends ImmutableInstruction implements PackedSwitchPayload {
    public static final Opcode OPCODE = Opcode.PACKED_SWITCH_PAYLOAD;

    @Nonnull public final ImmutableList<? extends ImmutableSwitchElement> switchElements;

    public ImmutablePackedSwitchPayload(@Nullable List<? extends SwitchElement> switchElements) {
        super(OPCODE);
        //TODO: need to validate that the keys are sequential
        this.switchElements = ImmutableSwitchElement.immutableListOf(switchElements);
    }

    public ImmutablePackedSwitchPayload(
            @Nullable ImmutableList<? extends ImmutableSwitchElement> switchElements) {
        super(OPCODE);
        this.switchElements = Objects.firstNonNull(switchElements, ImmutableList.<ImmutableSwitchElement>of());
    }

    @Nonnull
    public static ImmutablePackedSwitchPayload of(PackedSwitchPayload instruction) {
        if (instruction instanceof ImmutablePackedSwitchPayload) {
            return (ImmutablePackedSwitchPayload)instruction;
        }
        return new ImmutablePackedSwitchPayload(
                instruction.getSwitchElements());
    }

    @Nonnull @Override public List<? extends SwitchElement> getSwitchElements() { return switchElements; }

    @Override public int getCodeUnits() { return 4 + switchElements.size() * 2; }
    @Override public Format getFormat() { return OPCODE.format; }
}
