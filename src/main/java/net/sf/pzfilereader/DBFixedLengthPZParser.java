/*
 * ObjectLab, http://www.objectlab.co.uk/open is supporting PZFileReader.
 * 
 * Based in London, we are world leaders in the design and development 
 * of bespoke applications for the securities financing markets.
 * 
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 *
 * $Id: ColorProvider.java 74 2006-10-24 22:19:05Z benoitx $
 * 
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sf.pzfilereader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.sf.pzfilereader.util.PZConstants;
import net.sf.pzfilereader.util.ParserUtils;

/**
 * @author xhensevb
 * 
 */
public class DBFixedLengthPZParser extends AbstractFixedLengthPZParser {

    private Connection con;

    public DBFixedLengthPZParser(final Connection con, final InputStream dataSourceStream, final String dataDefinition) {
        super(dataSourceStream, dataDefinition);
        this.con = con;
    }

    public DBFixedLengthPZParser(final Connection con, final File dataSource, final String dataDefinition) {
        super(dataSource, dataDefinition);
        this.con = con;
    }

    protected void init() {
        try {
            final List cmds = ParserUtils.buildMDFromSQLTable(con, getDataDefinition());

            addToColumnMD(PZConstants.DETAIL_ID, cmds);
            addToColumnMD(PZConstants.COL_IDX, ParserUtils.buidColumnIndexMap(cmds));

            if (cmds.isEmpty()) {
                throw new FileNotFoundException("DATA DEFINITION CAN NOT BE FOUND IN THE DATABASE " + getDataDefinition());
            }

            setInitialised(true);
        } catch (final SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

    public DataSet doParse() {
        return null;
    }
}