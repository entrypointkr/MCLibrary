package kr.rvs.mclibrary;

import kr.rvs.mclibrary.general.Version;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Junhyeong Lim on 2017-08-27.
 */
public class VersionTest extends Assert {
    @Test
    public void versionParse() {
        Version version = new Version("1.12.1-R0.1-SNAPSHOT");
        assertEquals(1, version.getMajor());
        assertEquals(12, version.getMinor());
        assertEquals(1, version.getMaintenance());
    }

    @Test
    public void beforeAfter() {
        Version version = new Version(1, 12, 1);
        assertTrue(version.after(new Version(1, 12, 0)));
        assertFalse(version.after(new Version(1, 12, 1)));
        assertTrue(version.before(new Version(1, 12, 2)));
        assertFalse(version.before(new Version(1, 12, 1)));

        assertTrue(version.afterEquals(new Version(1, 12, 1)));
        assertTrue(version.afterEquals(new Version(1, 12, 0)));
        assertFalse(version.afterEquals(new Version(1, 12, 2)));
        assertTrue(version.beforeEquals(new Version(1, 12, 1)));
        assertTrue(version.beforeEquals(new Version(1, 12, 2)));
        assertFalse(version.beforeEquals(new Version(1, 12, 0)));
    }
}
