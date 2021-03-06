/*
 * Copyright 2012 MarkLogic Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marklogic.client.example.handle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.marklogic.client.io.Format;
import com.marklogic.client.MarkLogicIOException;
import com.marklogic.client.io.BaseHandle;
import com.marklogic.client.io.OutputStreamSender;
import com.marklogic.client.io.marker.BufferableHandle;
import com.marklogic.client.io.marker.StructureReadHandle;
import com.marklogic.client.io.marker.StructureWriteHandle;
import com.marklogic.client.io.marker.XMLReadHandle;
import com.marklogic.client.io.marker.XMLWriteHandle;

/**
 * A DOM4JHandle represents XML content as a dom4j document for reading or writing.
 */
public class DOM4JHandle
	extends BaseHandle<InputStream, OutputStreamSender>
	implements OutputStreamSender, BufferableHandle,
    	XMLReadHandle, XMLWriteHandle,
    	StructureReadHandle, StructureWriteHandle
{
	private SAXReader    reader;
	private OutputFormat outputFormat;
	private Document     content;

	public DOM4JHandle() {
		super();
		super.setFormat(Format.XML);
   		setResendable(true);
	}
	public DOM4JHandle(Document content) {
		this();
    	set(content);
	}

	public Document get() {
		return content;
	}
    public void set(Document content) {
    	this.content = content;
    }
    public DOM4JHandle with(Document content) {
    	set(content);
    	return this;
    }

	public void setFormat(Format format) {
		if (format != Format.XML)
			throw new IllegalArgumentException("JDOMHandle supports the XML format only");
	}

	public SAXReader getReader() {
		if (reader == null)
			reader = makeReader();

		return reader;
	}
	public void setReader(SAXReader reader) {
		this.reader = reader;
	}
	protected SAXReader makeReader() {
		SAXReader reader = new SAXReader();
		reader.setValidation(false);
		return reader;
	}

	public OutputFormat getOutputFormat() {
		return outputFormat;
	}
	public void setOutputFormat(OutputFormat outputFormat) {
		this.outputFormat = outputFormat;
	}

	@Override
	public void fromBuffer(byte[] buffer) {
		if (buffer == null || buffer.length == 0)
			content = null;
		else
			receiveContent(new ByteArrayInputStream(buffer));
	}
	@Override
	public byte[] toBuffer() {
		try {
			if (content == null)
				return null;

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			write(buffer);

			return buffer.toByteArray();
		} catch (IOException e) {
			throw new MarkLogicIOException(e);
		}
	}
	@Override
	public String toString() {
		try {
			return new String(toBuffer(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new MarkLogicIOException(e);
		}
	}

	@Override
	protected Class<InputStream> receiveAs() {
		return InputStream.class;
	}
	@Override
	protected void receiveContent(InputStream content) {
		if (content == null)
			return;

		try {
			this.content = getReader().read(
					new InputStreamReader(content, "UTF-8")
					);
		} catch (IOException e) {
			throw new MarkLogicIOException(e);
		} catch (DocumentException e) {
			throw new MarkLogicIOException(e);
		}
	}

	@Override
	protected OutputStreamSender sendContent() {
		if (content == null) {
			throw new IllegalStateException("No document to write");
		}

		return this;
	}
	@Override
	public void write(OutputStream out) throws IOException {
		Writer writer = new OutputStreamWriter(out, "UTF-8");
		OutputFormat outputFormat = getOutputFormat();
		if (outputFormat != null) {
			new XMLWriter(writer, outputFormat).write(content);
		} else {
			new XMLWriter(writer).write(content);
		}
		writer.flush();
	}

}
