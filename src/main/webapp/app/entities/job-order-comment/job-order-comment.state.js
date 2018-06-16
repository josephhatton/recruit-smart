(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('job-order-comment', {
        parent: 'entity',
        url: '/job-order-comment',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'JobOrderComments'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/job-order-comment/job-order-comments.html',
            controller: 'JobOrderCommentController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('job-order-comment-detail', {
        parent: 'job-order-comment',
        url: '/job-order-comment/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'JobOrderComment'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/job-order-comment/job-order-comment-detail.html',
            controller: 'JobOrderCommentDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'JobOrderComment', function ($stateParams, JobOrderComment) {
            return JobOrderComment.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'job-order-comment',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('job-order-comment-detail.edit', {
        parent: 'job-order-comment-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order-comment/job-order-comment-dialog.html',
            controller: 'JobOrderCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['JobOrderComment', function (JobOrderComment) {
                return JobOrderComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('job-order-comment.new', {
        parent: 'job-order-comment',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order-comment/job-order-comment-dialog.html',
            controller: 'JobOrderCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  description: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('job-order-comment', null, {reload: 'job-order-comment'});
          }, function () {
            $state.go('job-order-comment');
          });
        }]
      })
      .state('job-order-comment.edit', {
        parent: 'job-order-comment',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order-comment/job-order-comment-dialog.html',
            controller: 'JobOrderCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['JobOrderComment', function (JobOrderComment) {
                return JobOrderComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('job-order-comment', null, {reload: 'job-order-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('job-order-comment.delete', {
        parent: 'job-order-comment',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order-comment/job-order-comment-delete-dialog.html',
            controller: 'JobOrderCommentDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['JobOrderComment', function (JobOrderComment) {
                return JobOrderComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('job-order-comment', null, {reload: 'job-order-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
