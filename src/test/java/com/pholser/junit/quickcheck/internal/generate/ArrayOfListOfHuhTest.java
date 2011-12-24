/*
 The MIT License

 Copyright (c) 2010-2011 Paul R. Holser, Jr.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.pholser.junit.quickcheck.internal.generate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.pholser.junit.quickcheck.internal.extractors.IntegerExtractor;
import com.pholser.junit.quickcheck.reflect.GenericArrayTypeImpl;
import com.pholser.junit.quickcheck.reflect.ParameterizedTypeImpl;
import com.pholser.junit.quickcheck.reflect.WildcardTypeImpl;

import static java.util.Arrays.*;
import static org.mockito.Mockito.*;

public class ArrayOfListOfHuhTest extends GeneratingUniformRandomValuesForTheoryParameterTest {
    @Override
    protected void primeSourceOfRandomness() {
        int integerIndex = Iterables.indexOf(source, Predicates.instanceOf(IntegerExtractor.class));
        when(random.nextInt(0, Iterables.size(source) - 4))
            .thenReturn(integerIndex).thenReturn(integerIndex).thenReturn(integerIndex);
        when(random.nextInt(-1, 1)).thenReturn(1);
        when(random.nextInt(-2, 2)).thenReturn(2).thenReturn(-2).thenReturn(0).thenReturn(-1);
    }

    @Override
    protected Type parameterType() {
        return new GenericArrayTypeImpl(
            new ParameterizedTypeImpl(List.class, new WildcardTypeImpl(new Type[] { Object.class }, new Type[0])));
    }

    @Override
    protected int sampleSize() {
        return 3;
    }

    @Override
    protected List<?> randomValues() {
        List<List<?>[]> values = new ArrayList<List<?>[]>();
        values.add(new List<?>[0]);
        values.add(new List<?>[] { asList(1) });
        values.add(new List<?>[] { asList(2, -2), asList(0, -1) });
        return values;
    }

    @Override
    public void verifyInteractionWithRandomness() {
        verify(random, times(5)).nextInt(0, Iterables.size(source) - 4);
        verify(random).nextInt(-1, 1);
        verify(random, times(4)).nextInt(-2, 2);
    }
}
