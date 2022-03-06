package com.faketube.api.util;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.faketube.api.service.UserService;
import com.faketube.store.entity.video.VideoEntity;
import com.faketube.store.repository.BlockUserRepository;
import com.faketube.store.repository.CommentRepository;
import com.faketube.store.repository.UserRepository;
import com.faketube.store.repository.VideoRepository;

@Component
public class AutoSupportUtil {
	@Value("${data.folder}")
	private String dataFolder;

	private static final Logger logger = LoggerFactory.getLogger(AutoSupportUtil.class);
	
	private final VideoRepository videoDao;
	private final UserRepository userDao;
	private final CommentRepository commentDao;
	private final BlockUserRepository blockUserDao;
	private final UserService userService;


	@Autowired
	public AutoSupportUtil(VideoRepository videoDao, UserRepository userDao, CommentRepository commentDao,
			BlockUserRepository blockUserDao, UserService userService) {
		super();
		this.videoDao = videoDao;
		this.userDao = userDao;
		this.commentDao = commentDao;
		this.blockUserDao = blockUserDao;
		this.userService = userService;
	}


	@Transactional
	@Scheduled(cron="0 0 3 * * *")
	public void cleanVideo() {
		List<VideoEntity> videoDeleted = videoDao.findAllByDeletedAtBefore(
				LocalDateTime.now()
				.minusMonths(1))
				.stream()
				.map(this::deleteVideo)
				.collect(Collectors.toList());
		
		logger.info(String.format("all videos deleted after a month: %s", videoDeleted));
	}
	
	
	@Transactional
	@Scheduled(cron="0 0 4 * * *")
	public void cleanUserProfile() {
		userDao.deleteAllByDeletedAtBefore(LocalDateTime.now().minusMonths(1));
	}
	
	@Transactional
	@Scheduled(cron="0 0 5 * * *")
	public void cleanCommentVideo() {
		commentDao.deleteAllByDeletedAtBefore(LocalDateTime.now().minusMonths(1));
	}
	
	@Transactional
	@Scheduled(cron="0 0 6 * * *")
	public void cleanBlockUserByAuthor() {
		blockUserDao.findAllByBlockedTimeBefore(LocalDateTime.now()).stream().forEach((b)->{
			userService.unblockUserByAuthorChannel(
					b.getBlockUser(), b.getAuthorChannel());
		});
	}
	
	
	
	
	private VideoEntity deleteVideo(VideoEntity video) {
		Path path = Path.of(dataFolder, video.getVideoId());
		File file = new File(path.toUri());
		recursiveDelete(file);
		videoDao.deleteById(video.getVideoId());
		return video;
	}
	
	private static void recursiveDelete(File file) {
        // до конца рекурсивного цикла
        if (!file.exists())
            return;
        //если это папка, то идем внутрь этой папки и вызываем рекурсивное удаление всего, что там есть
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                // рекурсивный вызов
                recursiveDelete(f);
            }
        }
        // вызываем метод delete() для удаления файлов и пустых(!) папок
        file.delete();
        System.out.println("Удаленный файл или папка: " + file.getAbsolutePath());
    }
	
	
	
	
}
