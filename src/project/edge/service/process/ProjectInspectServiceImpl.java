/**
 * 
 */
package project.edge.service.process;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.FolderPath;
import project.edge.dao.archive.ArchiveDao;
import project.edge.dao.process.ProjectInspectAttachmentDao;
import project.edge.dao.process.ProjectInspectDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectCheck;
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.entity.ProjectInspectAttachment;


/**
 * @author angel_000
 *         [t_project_inspect]对应的Service。
 */
@Service
public class ProjectInspectServiceImpl extends GenericServiceImpl<ProjectInspect, String>
        implements ProjectInspectService {

    @Resource
    private ProjectInspectDao projectInspectDao;

    @Resource
    private ArchiveDao archiveDao;

    @Resource
    private ProjectInspectAttachmentDao projectInspectAttachmentDao;

    @Override
    public Dao<ProjectInspect, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.projectInspectDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void create(ProjectInspect entity) {

        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

//        // 如果项目文件夹对应的Archive不存在，则创建
//        if (this.archiveDao.find(FolderPath.PROJECT_INSPECT) == null) {
//            this.archiveDao.create(this.getProjectRootDirArchive());
//        }
//
//        // 如果实体对应的文件夹Archive不存在，则创建
//        if (this.archiveDao.find(entity.getId()) == null) {
//            this.archiveDao.create(this.getDirArchive(entity));
//        }
        
        //如果年份文件夹存在，则创建
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf.format(date);
        
        if (this.archiveDao.find(year) == null) {
            this.archiveDao.create(this.getYearRootDirArchive(year));
        }
        
        if(this.archiveDao.find(entity.getProject().getId()) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive(year,entity.getProject()));
        }
        
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE) == null) {
            this.archiveDao.create(this.getSystemPlaceOnFileRootDirArchive(year,entity.getProject()));
        }
     
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.PROJECT_INSPECT) == null) {
            this.archiveDao.create(this.getProjectInspectRootDirArchive(year,entity));
        }
        
        
        this.projectInspectDao.create(entity);
        for (ProjectInspectAttachment a : entity.getProjectInspectAttachments()) {
            this.archiveDao.create(a.getArchive());
            this.projectInspectAttachmentDao.create(a);
        }

        // List<ProjectInspectAttachment> projectInspectAttachmentList = new ArrayList<>();
        // for (Archive a : entity.getArchives()) {
        // ProjectInspectAttachment projectInspectAttachment = new ProjectInspectAttachment();
        // projectInspectAttachment.setId(UUID.randomUUID().toString());
        // projectInspectAttachment.setArchive(a);
        // projectInspectAttachment.setProjectInspect(entity);
        // projectInspectAttachment.setIsImprove(OnOffEnum.ON.value());
        // projectInspectAttachmentList.add(projectInspectAttachment);
        // this.archiveDao.create(a);
        // }
        //
        // // this.projectInspectDao.create(entity);
        // this.projectInspectAttachmentDao.create(projectInspectAttachmentList);
    }

    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void update(ProjectInspect entity) {
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

//        // 如果项目文件夹对应的Archive不存在，则创建
//        if (this.archiveDao.find(FolderPath.PROJECT_INSPECT) == null) {
//            this.archiveDao.create(this.getProjectRootDirArchive());
//        }
//
//        // 如果实体对应的文件夹Archive不存在，则创建
//        if (this.archiveDao.find(entity.getId()) == null) {
//            this.archiveDao.create(this.getDirArchive(entity));
//        }
        //
        // // 创建新增的文件，同时将修改后保留的文件id存入map
        // for (Archive a : entity.getArchives()) {
        //
        // // 修改后保留的Archive在Converter中只设了id，其他字段未设置
        // if (!StringUtils.isEmpty(a.getRelativePath())) {
        // this.archiveDao.create(a);
        // }
        // }
        //
        // super.update(entity);
        //
        // for (Archive archiveToDelete : entity.getArchivesToDelete()) {
        // this.archiveDao.delete(archiveToDelete);
        // }
        
        //如果年份文件夹存在，则创建
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf.format(date);
        
        if (this.archiveDao.find(year) == null) {
            this.archiveDao.create(this.getYearRootDirArchive(year));
        }
        
        if(this.archiveDao.find(entity.getProject().getId()) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive(year,entity.getProject()));
        }
        
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE) == null) {
            this.archiveDao.create(this.getSystemPlaceOnFileRootDirArchive(year,entity.getProject()));
        }
        
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.PROJECT_INSPECT) == null) {
            this.archiveDao.create(this.getProjectInspectRootDirArchive(year,entity));
        }

        // 创建新增的文件
        for (ProjectInspectAttachment attachment : entity.getProjectInspectAttachments()) {

            this.archiveDao.create(attachment.getArchive());
            this.projectInspectAttachmentDao.create(attachment);
        }

        super.update(entity);

        for (ProjectInspectAttachment attachmentToDelete : entity.getAttachmentsToDelete()) {
            this.archiveDao.delete(attachmentToDelete.getArchive());
        }
    }

    @Transactional
    public void setData(ProjectInspect entity) {
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
    }
    
    /**
     * 创建年的根文件夹Archive。
     * 
     * @return
     */
    private Archive getYearRootDirArchive(String year) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(year);
        dir.setArchiveName(year + "年");
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(Constants.SLASH);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(1);
        dir.setPath(Constants.SLASH);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(Constants.ADMIN_USER_ID);

        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
    
    /**
     * 创建项目的根文件夹Archive。
     * 
     * @return
     */
    private Archive getProjectRootDirArchive(String year, Project entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getProjectName() + "_" + entity.getProjectNum());
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + year + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(year);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + year);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(Constants.ADMIN_USER_ID);

        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
    
    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getSystemPlaceOnFileRootDirArchive(String year, Project entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE);
        dir.setArchiveName("系统归档"); // 项目名不允许修改，所以不考虑变更项目名引起项目文件夹改名的问题
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + year + File.separator + entity.getId() + File.separator
                + dir.getId());
        dir.setFileSize(null);
        dir.setPid(entity.getId());

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(3);
        dir.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA + entity.getId());
        dir.setIsDeleted(OnOffEnum.OFF.value());
//        Person person = entity.getCreator();
//        if (person != null) {
//            dir.setCreator(person.getId());
//        }
        
        dir.setCreator(Constants.ADMIN_USER_ID);
        
        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
    
    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getProjectInspectRootDirArchive(String year, ProjectInspect entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getProject().getId() + "_" + FolderPath.PROJECT_INSPECT);
        dir.setArchiveName("项目巡查"); // 项目名不允许修改，所以不考虑变更项目名引起项目文件夹改名的问题
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + year + File.separator + entity.getProject().getId() + File.separator
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator
                + dir.getId());
        dir.setFileSize(null);
        dir.setPid(entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(4);
        dir.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA + entity.getProject().getId()
                + Constants.COMMA + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE);
        dir.setIsDeleted(OnOffEnum.OFF.value());
//        Person person = entity.getProject().getCreator();
//        if (person != null) {
//            dir.setCreator(person.getId());
//        }

        dir.setCreator(Constants.ADMIN_USER_ID);
        dir.setcDatetime(entity.getcDatetime());
        dir.setmDatetime(entity.getmDatetime());

        return dir;
    }
    
    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getDirArchive(ProjectInspect entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getInspectPlan());
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(
                File.separator + FolderPath.PROJECT_INSPECT + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(FolderPath.PROJECT_INSPECT);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT_GROUP(DIR)
         * 第二层：ProjectGroup.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PROJECT_INSPECT);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(entity.getCreator());
        dir.setcDatetime(entity.getcDatetime());
        dir.setmDatetime(entity.getmDatetime());

        return dir;
    }

    /**
     * 创建项目的根文件夹Archive。
     * 
     * @return
     */
    private Archive getProjectRootDirArchive() {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(FolderPath.PROJECT_INSPECT);
        dir.setArchiveName("项目巡查归档");
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(Constants.SLASH);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(1);
        dir.setPath(Constants.SLASH);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(Constants.ADMIN_USER_ID);

        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
}
